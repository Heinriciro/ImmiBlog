package com.ianhein.immiblog2.Service;

import com.ianhein.immiblog2.domain.Tag;
import com.ianhein.immiblog2.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pagable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTag(Integer size);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);

}
