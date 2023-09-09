package com.ttknpdev.understandsecurity.repository;

import java.util.List;

public interface CustomerRepository <T>{
    T read (String fullname);
    List<T> reads();
}
