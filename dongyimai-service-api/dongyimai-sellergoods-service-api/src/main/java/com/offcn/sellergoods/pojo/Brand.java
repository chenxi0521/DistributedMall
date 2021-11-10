package com.offcn.sellergoods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author chenxi
 * @date 2021/11/5 16:45
 * @description
 */
@TableName(value="tb_brand")
public class Brand implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    @TableField(value = "name")
    private String name;//品牌名称

    @TableField(value = "first_char")
    private String firstChar;//品牌首字母

    @TableField(value = "image")
    private String image;//品牌图像

    public Brand() {
    }

    public Brand(Long id, String name, String firstChar, String image) {
        this.id = id;
        this.name = name;
        this.firstChar = firstChar;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}