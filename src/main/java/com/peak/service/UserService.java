package com.peak.service;

import com.peak.pojo.User;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/5 14:37
 */
public interface UserService {
  public User cheakUser(String username, String password);
}
