package com.example.ojbot.utils.root;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Data
@AllArgsConstructor
public class RootParam {

    private String msg_type;
    private Object content;
}
