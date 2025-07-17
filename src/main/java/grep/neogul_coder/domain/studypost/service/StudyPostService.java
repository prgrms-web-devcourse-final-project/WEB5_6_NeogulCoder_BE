package grep.neogul_coder.domain.studypost.service;

import grep.neogul_coder.domain.studypost.StudyPost;
import grep.neogul_coder.domain.studypost.exception.PostNotFoundException;
import grep.neogul_coder.domain.studypost.exception.code.PostErrorCode;
import grep.neogul_coder.domain.studypost.repository.StudyPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyPostService {

    private final StudyPostRepository StudyPostRepository;

    public StudyPost findById(Long id) {
        return StudyPostRepository.findById(id).orElseThrow(() -> new PostNotFoundException(
            PostErrorCode.POST_NOT_FOUND));
    }

}
