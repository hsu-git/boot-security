package org.example.bootsecurity.model.mapper;

import org.apache.ibatis.annotations.*;
import org.example.bootsecurity.model.domain.Memo;
import org.springframework.stereotype.Repository;

import java.util.List;

// Spring
@Repository
// MyBatis
@Mapper
public interface MemoMapper {
    @Select("SELECT * FROM memo")
    List<Memo> findAll();

    @Insert("INSERT INTO memo (text) VALUES (#{text})")
//    Supabase는 알아서 auto 증가를 하고 있는데.. 이걸 개입하려고 해서 문제 발생
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Memo memo);

    // TIL : 소프트 딜리트 (Soft Delete) <-> 하드 딜리트(hard delete)
    // 이건 하드 딜리트
    @Delete("DELETE FROM memo")
    void deleteAll();

    @Delete("DELETE FROM memo WHERE id= #{id}")
    void deleteById(Long id);
}
