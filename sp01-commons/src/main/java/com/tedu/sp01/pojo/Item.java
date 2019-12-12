package com.tedu.sp01.pojo;

/**
 * @program: sp01-commons
 * @author: JL
 * @create: 2019-11-27 18:05
 * @description:
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private Integer number;
}
