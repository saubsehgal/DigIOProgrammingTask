package com.digio.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApacheLogAttributes {
  
  private int count;
  
  private Date date;
}
