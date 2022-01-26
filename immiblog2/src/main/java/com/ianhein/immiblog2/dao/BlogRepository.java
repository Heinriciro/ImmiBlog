package com.ianhein.immiblog2.dao;

import com.ianhein.immiblog2.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    
    Blog findByTitle(String title);

    @Query("select t from t_blog t where t.recommended = true")
    List<Blog>  findTop(Pageable pageable);

    @Query("select t from t_blog t where t.title like ?1 or t.content like ?1")
    Page<Blog> findByQuery(String Query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update t_blog b set b.views = b.views+1 where b.id=?1")
    int updateViews(Long id);


    @Query("select function('date_format',b.updateTime,'%Y') as year from t_blog b group by function('date_format',b.updateTime,'%Y') order by year DESC")
    List<String> findGroupYear();

    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y') = ?1 order by b.updateTime DESC ")
    List<Blog> findByYear(String year);
}
