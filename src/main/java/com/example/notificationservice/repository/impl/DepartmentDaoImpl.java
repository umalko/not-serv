package com.example.notificationservice.repository.impl;

import com.example.notificationservice.model.Department;
import com.example.notificationservice.repository.DepartmentDao;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DepartmentDaoImpl implements DepartmentDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Department> findAll() {

//        List<Object[]> chatIds = jdbcTemplate.query(
//                "SELECT chat_id FROM unanswered_chats WHERE received_at < ?", new Object[]{receivedBefore.getEpochSecond()},
//                (rs, rowNum) -> new Object[]{rs.getString("chat_id")});
        return Lists.newArrayList();
    }
}
