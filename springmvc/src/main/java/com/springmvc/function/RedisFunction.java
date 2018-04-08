package com.springmvc.function;

public interface RedisFunction<T, R> {

	R cacheOp(T t);
}
