/**
 * @projectName demo
 * @package com.thunisoft.demo.demo
 * @className com.thunisoft.demo.demo.Api
 * @copyright Copyright 2020 Thuisoft, Inc. All rights reserved.
 */
package com.thunisoft.demo.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Api
 *
 * @description API
 * @author maguoliang
 * @date 2020/9/8 16:54
 * @version v1.1.3
 */
@Target({ElementType.TYPE, ElementType.METHOD}) // 作用于类、接口等与方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

    String description() default "1111111";
}
