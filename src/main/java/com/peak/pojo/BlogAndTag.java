package com.peak.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/7 18:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAndTag {
    private Long blogId;
    private Long TagId;
}
