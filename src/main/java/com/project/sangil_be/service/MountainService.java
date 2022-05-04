package com.project.sangil_be.service;

import com.project.sangil_be.dto.SearchBeforeDto;
import com.project.sangil_be.model.Mountain100;
import com.project.sangil_be.model.MountainComment;
import com.project.sangil_be.repository.Mountain100Repository;
import com.project.sangil_be.repository.MountainCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MountainService {
    private final Mountain100Repository mountain100Repository;
    private final MountainCommentRepository mountainCommentRepository;

    public List<SearchBeforeDto.MountainInfo> Show10() {
        List<Mountain100> mountain100List = mountain100Repository.findAll();

        Collections.shuffle(mountain100List);

        List<SearchBeforeDto.MountainInfo> searchBeforeDto = new ArrayList<>();

        int star=0;
        float starAvr;
        for (int i = 0; i < 10; i++) {
            List<MountainComment> mountainComments = mountainCommentRepository.findAllByMountain100Id(mountain100List.get(i).getMountain100Id());
            if (mountainComments.size() == 0) {
                starAvr = 0;
            } else {
                for (int j = 0; j < mountainComments.size(); j++) {
                    star+=mountainComments.get(i).getStar();
                }
                starAvr = (float)star/mountainComments.size();
            }
            SearchBeforeDto.MountainInfo mountainInfo = new SearchBeforeDto.MountainInfo(
                    mountain100List.get(i).getMountain100Id(),
                    mountain100List.get(i).getMountain(),
                    mountain100List.get(i).getMountainAddress(),
                    mountain100List.get(i).getMountainImgUrl(),
                    starAvr,
                    String.format("%.7f",mountain100List.get(i).getLat()),
                    String.format("%.7f",mountain100List.get(i).getLng()));
            searchBeforeDto.add(mountainInfo);
        }

        return searchBeforeDto;

    }
}
