package org.oiue.tools.bytes;

public class Crc {
	/**
	 * 获得CRC校验结果，除校验位字节外，其他所有字节的异或值
	 * @param crcing 校验源
	 * @param pos 起始下标
	 * @param len 长度
	 * @return 校验码
	 * @throws IllegalArgumentException 校验失败
	 */
	public static byte crc(byte[] crcing, int pos, int len) throws IllegalArgumentException {
		if (crcing == null) {
			throw new IllegalArgumentException("待校验字节数组不能为空");
		}
		if (pos < 0 || (pos + len) > crcing.length) {
			throw new IllegalArgumentException("pos(" + pos + ")必须大于0，且pos(" + pos + ") + len(" + len + ")必须小于等于crcing.length(" + crcing.length + ")");
		}
		byte crced = 0;
		for (int i = pos; i < (pos + len); i++) {
			crced ^= crcing[i];
		}
		return crced;
	}
}
