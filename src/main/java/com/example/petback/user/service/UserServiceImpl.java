package com.example.petback.user.service;

import com.example.petback.comment.entity.Comment;
import com.example.petback.comment.repository.CommentRepository;
import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.common.jwt.RefreshToken;
import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.entity.FeedLike;
import com.example.petback.feed.repository.FeedLikeRepository;
import com.example.petback.feed.repository.FeedRepository;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservation.repository.ReservationRepository;
import com.example.petback.review.entity.Review;
import com.example.petback.review.repository.ReviewRepository;
import com.example.petback.tip.entity.Tip;
import com.example.petback.tip.entity.TipLike;
import com.example.petback.tip.repository.TipLikeRepository;
import com.example.petback.tip.repository.TipRepository;
import com.example.petback.user.dto.ProfileRequestDto;
import com.example.petback.user.dto.ProfileResponseDto;
import com.example.petback.user.dto.SignupRequestDto;
import com.example.petback.user.entity.User;
import com.example.petback.user.repository.RefreshTokenRepository;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final CommentRepository commentRepository;
    private final TipRepository tipRepository;
    private final TipLikeRepository tipLikeRepository;
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;
    private final HospitalRepository hospitalRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    // 회원 가입
    @Override
    public void signUp(SignupRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent() || userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디 또는 이메일 입니다.");
        }
        userRepository.save(requestDto.toEntity());
    }

    // 회원정보 전체 조회
    @Override
    public List<ProfileResponseDto> selectProfiles() {
        return userRepository.findAll().stream().map(ProfileResponseDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto selectMyProfile(User user) {
        return new ProfileResponseDto(user);
    }

    // 회원정보 상세 조회
    @Override
    public ProfileResponseDto selectProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return new ProfileResponseDto(user);
    }

    // 회원정보 수정
    @Override
    public void updateProfile(ProfileRequestDto requestDto, User user) {
        User dbUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        dbUser.updateProfile(requestDto);
    }

    // 회원 탈퇴
    // User 의 경우
    //  피드, 피드 댓글, 피드 좋아요, 채팅방 메시지, 예약, 리뷰 > 탈퇴시 지워짐
    // owner 의 경우
    // 병원 > 탈퇴시 지워짐 species , subjects는?

    @Transactional
    @Override
    public void deleteProfile(User user, Long id) {
        User userToDelete = findUser(id);
        if (!user.equals(userToDelete)) {
            throw new IllegalArgumentException("탈퇴 권한이 없습니다.");
        }

        userToDelete.getComments().forEach(Comment::setDeleted);
        userToDelete.getFeeds().forEach(Feed::setDeleted);
        userToDelete.getFeedLikes().forEach(FeedLike::setDeleted);
        userToDelete.getHospitals().forEach(Hospital::setDeleted);
        userToDelete.getReservations().forEach(Reservation::setDeleted);
        userToDelete.getReviews().forEach(Review::setDeleted);
        userToDelete.getTips().forEach(Tip::setDeleted);
        userToDelete.getTipLikes().forEach(TipLike::setDeleted);

        // UserRepository 를 통해 변경 사항을 저장
        userRepository.flush();


        // User 엔티티의 삭제 상태 설정 및 저장
        userToDelete.setDeleted(true);
        userRepository.save(userToDelete);
    }

    // ---------------------------- 회원탈퇴 후 복구시 삭제된 데이터 복구
    @Override
    @Transactional
    public void restoreProfile(Long userId) {

        User userToRestore = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        // 복구 가능한 상태인지 확인
        if (!userToRestore.isDeleted()) {
            throw new IllegalArgumentException("이미 복구된 사용자입니다.");
        }
        userToRestore.restore();
        userRepository.save(userToRestore);

        restoreSoftDeletedFeeds(userToRestore);
        restoreSoftDeletedFeedLikes(userToRestore);
        restoreSoftDeletedComments(userToRestore);
        restoreSoftDeletedHospitals(userToRestore);
        restoreSoftDeletedReservations(userToRestore);
        restoreSoftDeletedReviews(userToRestore);
        restoreSoftDeletedTips(userToRestore);
        restoreSoftDeletedTipLikes(userToRestore);

        // 복구 가능한 상태라면 삭제 상태를 false로 변경하고 저장
        userRepository.flush();
        userRepository.save(userToRestore);

    }


    @Override
    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    @Override
    public Map<String, String> refreshToken(String refreshToken) throws NoSuchElementException {
        if (refreshToken == null) throw new NoSuchElementException("refreshToken이 만료되었습니다.");
        RefreshToken redisToken = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new NoSuchElementException("refreshToken이 만료되었습니다.")); // 무조건 @Id로만 찾기
        Long userId = redisToken.getUserId();
        User user = userRepository.findById(userId).get();
        Map<String, String> tokens = new HashMap<>();
        String newToken = jwtUtil.createToken(user.getUsername(), user.getRole(), user.getId());
        tokens.put("accessToken", newToken);
        tokens.put("refreshToken", refreshToken); // 기존 refreshToken 유효하므로 그대로 반환
        return tokens;
    }

    //softDelete된 데이터도 검색
    @Override
    public Long getUserIdByEmail(String email) {

        User user = userRepository.findUserByEmailIgnoringSoftDelete(email);
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    /*복구해야 하는 것들
     * Feed, FeedLikes, Comment, Hospital
     * Reservations, Review, Tip, TipLikes 8개 */
    @Transactional
    public void restoreSoftDeletedFeeds(User userToRestore) {
        List<Feed> softDeletedFeeds = feedRepository.findSoftDeletedFeedsByUserId(userToRestore.getId());

        for (Feed feed : softDeletedFeeds) {
            feed.restore();
        }
    }

    @Transactional
    public void restoreSoftDeletedFeedLikes(User userToRestore) {
        List<FeedLike> softDeletedFeedLikes = feedLikeRepository.findSoftDeletedFeedLikesByUserId(userToRestore.getId());

        for (FeedLike feedLike : softDeletedFeedLikes) {
            feedLike.restore();
        }
    }

    @Transactional
    public void restoreSoftDeletedComments(User userToRestore) {
        List<Comment> softDeletedComments = commentRepository.findSoftDeletedCommentsByUserId(userToRestore.getId());

        for (Comment comment : softDeletedComments) {
            comment.restore();
        }
    }

    @Transactional
    public void restoreSoftDeletedHospitals(User userToRestore) {
        List<Hospital> softDeletedHospitals = hospitalRepository.findSoftDeletedHospitalsByUserId(userToRestore.getId());

        for (Hospital hospital : softDeletedHospitals) {
            hospital.restore();
        }
    }

    @Transactional
    public void restoreSoftDeletedReservations(User userToRestore) {
        List<Reservation> softDeletedReservations = reservationRepository.findSoftDeletedReservationsByUserId(userToRestore.getId());

        for (Reservation reservation : softDeletedReservations) {
            reservation.restore();
        }
    }

    @Transactional
    public void restoreSoftDeletedReviews(User userToRestore) {
        List<Review> softDeletedReviews = reviewRepository.findSoftDeletedReviewsByUserId(userToRestore.getId());

        for (Review review : softDeletedReviews) {
            review.restore();
        }
    }

    @Transactional
    public void restoreSoftDeletedTips(User userToRestore) {
        List<Tip> softDeletedTips = tipRepository.findSoftDeletedTipsByUserId(userToRestore.getId());

        for (Tip tip : softDeletedTips) {
            tip.restore();
        }
    }

    @Transactional
    public void restoreSoftDeletedTipLikes(User userToRestore) {
        List<TipLike> softDeletedTipLikes = tipLikeRepository.findSoftDeletedTipLikesByUserId(userToRestore.getId());

        for (TipLike tipLike : softDeletedTipLikes) {
            tipLike.restore();
        }
    }

}
