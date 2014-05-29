package com.angle.test.mock;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 测试
 * @author hadoop
 *
 */
public class SimpleMockTest {

	@Test
	public void test() {
		
		List<String> list = mock(List.class);
		when(list.get(0)).thenReturn("testList");
		String actual = list.get(0);
		assertEquals("testList", actual);
        System.out.println("hello world!");
    }

}
