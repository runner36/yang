package com.winchannel.task.model;

import java.util.Date;


/**
 * InterfaceLog entity. @author MyEclipse Persistence Tools
 */

public class InterfaceLog  implements java.io.Serializable {


    // Fields    

     private Long id;
     private Date createTime;
     private String model;
     private String message;


    // Constructors

    /** default constructor */
    public InterfaceLog() {
    }

    
    /** full constructor */
    public InterfaceLog(Date createTime, String model, String message) {
        this.createTime = createTime;
        this.model = model;
        this.message = message;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModel() {
        return this.model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }

    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
   








}