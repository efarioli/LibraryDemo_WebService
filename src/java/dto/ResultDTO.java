/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author f023507i
 */
public class ResultDTO implements Serializable
        
{
    private String result;

    public ResultDTO(String result)
    {
        this.result = result;
    }
    
}
