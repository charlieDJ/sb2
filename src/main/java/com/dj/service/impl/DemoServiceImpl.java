package com.dj.service.impl;

import com.dj.dto.User;
import com.dj.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public User getUserByName() {
        return new User();
    }
}
