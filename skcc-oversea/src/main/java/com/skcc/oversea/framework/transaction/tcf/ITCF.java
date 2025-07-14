package com.skcc.oversea.framework.transaction.tcf;

import org.springframework.stereotype.Component;

@Component
public interface ITCF {

  void initialize();

  void cleanup();

  boolean isAvailable();

}
