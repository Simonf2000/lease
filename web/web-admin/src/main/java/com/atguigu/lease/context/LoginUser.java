package com.atguigu.lease.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: smionf
 * @Date: 2024/01/25/13:55
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    private Long userId;
    private String username;
}