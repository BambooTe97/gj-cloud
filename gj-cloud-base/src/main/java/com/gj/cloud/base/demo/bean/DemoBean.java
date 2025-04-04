package com.gj.cloud.base.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
@Table("demo")
@Data
public class DemoBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -6463137026815433731L;

    @Id
    private Long id;

    private String userName;

    private String enName;

    private Integer age;

    private String sex;

    private String address;

    private String createName;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
