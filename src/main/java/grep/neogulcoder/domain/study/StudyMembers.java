package grep.neogulcoder.domain.study;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudyMembers {

    private List<StudyMember> studyMembers;

    private StudyMembers(List<StudyMember> studyMembers) {
        this.studyMembers = studyMembers;
    }

    public static StudyMembers of(List<StudyMember> studyMembers){
        return new StudyMembers(studyMembers);
    }

    public List<StudyMember> excludeByUser(long userId){
        return this.studyMembers.stream()
                .filter(studyMember -> studyMember.getUserId() != userId)
                .toList();
    }

    public Map<Long, Long> getGroupedStudyIdCountMap() {
        return this.studyMembers.stream()
                .map(StudyMember::getStudy)
                .collect(
                        Collectors.groupingBy(
                                Study::getId,
                                Collectors.counting()
                        )
                );
    }
}
