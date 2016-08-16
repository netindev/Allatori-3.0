package com.allatori.watermark;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import com.allatori.Class171;
import com.allatori.Class34;
import com.allatori.Class42;
import com.allatori.Exception_Sub1;
import com.allatori.Tuning;
import com.allatori.clazz.ClassStorage;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class Watermarking {

	private String string;
	private ClassStorage classStorage;

	// Method cleaned.
	public String extractWatermark() throws Exception_Sub1 {
		try {
			Vector<Class171> vector = this.method1761();
			short[] arrShort = this.method1759(vector);
			return this.method1765(arrShort);
		} catch (Exception e) {
			throw new Exception_Sub1("Cannot extract watermark.");
		}
	}

	// Method cleaned.
	private short[] method1759(Vector<Class171> parVector) {
		Vector<Number> vector = new Vector<Number>();
		for (int i = parVector.size() - 1; i >= 0; i--) {
			for (InstructionHandle instructionHandle = Class171.method1704((Class171) parVector.get(i)).getInstructionList()
					.getStart(); instructionHandle != null; instructionHandle.getNext()) {
				if (this.method1762(instructionHandle)) {
					vector.add(((SIPUSH) instructionHandle.getInstruction()).getValue());
					vector.add(((SIPUSH) instructionHandle.getNext().getInstruction()).getValue());
					vector.add(((SIPUSH) instructionHandle.getNext().getNext().getInstruction()).getValue());
					vector.add(((SIPUSH) instructionHandle.getNext().getNext().getNext().getInstruction()).getValue());
				}
			}
		}
		short[] arrShort = new short[vector.size()];
		for (int i = arrShort.length - 1; i >= 0; i--) {
			arrShort[i] = ((Number) vector.get(i)).shortValue();
		}
		return arrShort;
	}

	private void method1760(Vector<Class171> var1, short[] var2) {
		int var3 = var2.length / 4;
		int var4 = var1.size();
		int var5 = (var3 - 1) / var4 + 1;
		int var6 = 0;
		int var7;
		int var10000 = var7 = var1.size() - 1;
		while (var10000 >= 0) {
			Class171 var8;
			InstructionList var9;
			int var10;
			if ((var10 = (var9 = Class171.method1704(var8 = (Class171) var1.get(var7)).getInstructionList())
					.getLength()) > 0) {
				var10 = Class34.method571(var10);
			}
			InstructionHandle var11 = var9.getStart();
			var10000 = var10;
			while (true) {
				var10 = var10000 - 1;
				if (var10000 <= 0) {
					if (Tuning.isWeakStringEncryption()) {
						var11 = var9.getEnd();
					}
					int var12 = 0;
					do {
						var9.insert(var11, new SIPUSH(var2[var6++]));
						var9.insert(var11, new SIPUSH(var2[var6++]));
						var9.insert(var11, new SIPUSH(var2[var6++]));
						var9.insert(var11, new SIPUSH(var2[var6++]));
						var9.insert(var11, new POP2());
						var9.insert(var11, new POP2());
						if (!Tuning.isWeakStringEncryption()) {
							if (var12 == 0) {
								var11 = var9.getStart();
							}
							if (var12 == 1) {
								var11 = var9.getEnd();
							}
						}
						++var12;
					} while (var12 < var5 && var7 < var3 - var6 / 4);
					Class171.method1704(var8).setMaxStack();
					Class171.method1705(var8).replaceMethod(Class171.method1706(var8),
							Class171.method1704(var8).getMethod());
					--var7;
					var10000 = var7;
					break;
				}
				var11 = var11.getNext();
				var10000 = var10;
			}
		}

	}

	public Watermarking(ClassStorage classStorage, String string) {
		this.classStorage = classStorage;
		this.string = string;
		Method.setComparator(Class42.method652());
	}

	private Vector<Class171> method1761() {
		Vector<Class171> var1 = new Vector<Class171>();
		Iterator<?> var2;
		int var5;
		for (Iterator<?> var10000 = var2 = this.classStorage.method671(); var10000.hasNext(); var10000 = var2) {
			ClassGen var3;
			Method[] var4;
			for (int var7 = var5 = (var4 = (var3 = (ClassGen) var2.next()).getMethods()).length
					- 1; var7 >= 0; var7 = var5) {
				Method var6;
				if ((var6 = var4[var5]).getCode() != null) {
					var1.add(new Class171(var3, var6));
				}
				--var5;
			}
		}
		return var1;
	}

	private boolean method1762(InstructionHandle var1) {
		return var1.getInstruction() instanceof SIPUSH && var1.getNext() != null
				&& var1.getNext().getInstruction() instanceof SIPUSH && var1.getNext().getNext() != null
				&& var1.getNext().getNext().getInstruction() instanceof SIPUSH
				&& var1.getNext().getNext().getNext() != null
				&& var1.getNext().getNext().getNext().getInstruction() instanceof SIPUSH
				&& var1.getNext().getNext().getNext().getNext() != null
				&& var1.getNext().getNext().getNext().getNext().getInstruction() instanceof POP2
				&& var1.getNext().getNext().getNext().getNext().getNext() != null
				&& var1.getNext().getNext().getNext().getNext().getNext().getInstruction() instanceof POP2;
	}

	// Method cleaned.
	private boolean method1763(Vector<Class171> parVector) {
		for (int i = parVector.size() - 1; i >= 0; i--) {
			InstructionList instructionList = Class171.method1704((Class171) parVector.get(i)).getInstructionList();
			for (InstructionHandle instructionHandle = instructionList.getStart(); instructionHandle != null; instructionHandle.getNext()) {
				if (this.method1762(instructionHandle)) {
					return true;
				}
			}
		}
		return false;
	}

	private short[] method1764(String var1) throws Exception {
		byte[] var2;
		int var3;
		int var10000;
		for (var10000 = var3 = (var2 = var1.getBytes("UTF-8")).length; var10000 % 4 != 0; var10000 = var3) {
			++var3;
		}
		byte[] var4;
		Arrays.fill(var4 = new byte[var3], (byte) 0);
		var4[0] = var2[0];
		if (var2.length > 1) {
			var4[1] = (byte) (var4[1] | var2[1] ^ var2[0]);
		}
		int var5;
		for (var10000 = var5 = 2; var10000 < var2.length; var10000 = var5) {
			var4[var5] = (byte) (var4[var5] | var2[var5] ^ var2[var5 - 1] ^ var2[var5 - 2]);
			++var5;
		}
		int var6;
		for (var10000 = var6 = var2.length; var10000 < var3; var10000 = var6) {
			var4[var6] = (byte) (var4[var6] | Class34.method571(256));
			++var6;
		}
		byte[] var7 = this.string.getBytes();
		int var8;
		for (var10000 = var8 = 0; var10000 < var4.length; var10000 = var8) {
			var4[var8] ^= var7[var8 % var7.length];
			++var8;
		}
		int var9 = var3;
		int var10;
		for (var10000 = var10 = (int) ((double) var3 * (0.666D + Class34.method566() / 3.0D)) + 113; var10000
				% 4 != 0; var10000 = var10) {
			++var10;
		}
		short[] var11;
		Arrays.fill(var11 = new short[var3 + var10], (short) 0);
		int var12;
		for (var10000 = var12 = 0; var10000 < var11.length; var10000 = var12) {
			if (var12 < var9) {
				var11[var12 + 2] = (short) (var11[var12 + 2] | var4[var12] << 8 | var4[var12 + 2] & 255);
				var11[var12 + 3] = (short) (var11[var12 + 3] | var4[var12 + 1] << 8 | var4[var12 + 3] & 255);
			} else {
				var11[var12 + 2] = (short) (var11[var12 + 2] | Class34.method571(Integer.MAX_VALUE));
				var11[var12 + 3] = (short) (var11[var12 + 3] | Class34.method571(Integer.MAX_VALUE));
			}
			var12 += 4;
		}
		Hashtable<Short, String> var13 = new Hashtable<Short, String>();
		boolean var14 = false;
		short var23 = (short) (this.string.hashCode());
		var13.put(var23, "");
		int var15;
		for (var10000 = var15 = 0; var10000 < var11.length; var10000 = var15) {
			short var16 = (short) (Class34.method571(65536) - '\u8000');
			for (Hashtable<Short, String> var25 = var13; var25.containsKey(new Short(var16)); var25 = var13) {
				++var16;
			}
			var13.put(var16, "");
			var11[var15] = var16;
			if (var15 > 3) {
				var11[var15 - 3] = var16;
			}
			var15 += 4;
		}
		int var24;
		for (var10000 = var24 = var9 + 4 * Class34.method571(20); var10000 < var11.length; var10000 = var24) {
			var11[var24 + 1] = var11[Class34.method571(var9 / 4) * 4];
			var24 += 4;
		}
		int var17 = this.string.hashCode();
		var11[var11.length - 4] = var11[var11.length - 3] = var11[var11.length - 2] = var11[var11.length - 1] = 0;
		var11[var11.length - 4] = (short) (var11[var11.length - 4] | var17);
		var11[var11.length - 3] = (short) (var11[var11.length - 3] | var17 >> 16);
		var11[var11.length - 2] |= var11[0];
		var11[var11.length - 1] = (short) (var11[var11.length - 1] | var2.length);
		int var18;
		for (var10000 = var18 = 0; var10000 < var11.length; var10000 = var18) {
			short var19 = var11[var18];
			short var20 = var11[var18 + 1];
			short var21 = var11[var18 + 2];
			short var22 = var11[var18 + 3];
			var11[var18] = var11[var18 + 1] = var11[var18 + 2] = var11[var18 + 3] = 0;
			var11[var18] = (short) (var11[var18] | var19 ^ var20 ^ var21);
			var11[var18 + 1] = (short) (var11[var18 + 1] | var20 ^ var21 ^ var22);
			var11[var18 + 2] = (short) (var11[var18 + 2] | var19 ^ var21 ^ var22);
			var11[var18 + 3] = (short) (var11[var18 + 3] | var19 ^ var20 ^ var22);
			var18 += 4;
		}
		return var11;
	}

	private String method1765(short[] var1) throws Exception {
		int var2;
		short var4;
		short var5;
		int var10000;
		for (var10000 = var2 = 0; var10000 < var1.length; var10000 = var2) {
			short var3 = var1[var2];
			var4 = var1[var2 + 1];
			var5 = var1[var2 + 2];
			short var6 = var1[var2 + 3];
			var1[var2] = var1[var2 + 1] = var1[var2 + 2] = var1[var2 + 3] = 0;
			var1[var2] = (short) (var1[var2] | var3 ^ var5 ^ var6);
			if (var1[var2] % 2 == 0) {
				var1[var2 + 1] = (short) (var1[var2 + 1] | var3 ^ var4 ^ var6);
			} else {
				var1[var2 + 1] = (short) (var1[var2 + 1] | var4 ^ var5 ^ var1[var2]);
			}
			if (var1[var2 + 1] % 4 == 0) {
				var1[var2 + 2] = (short) (var1[var2 + 2] | var3 ^ var4 ^ var5);
			} else if (var1[var2 + 1] % 4 == 1) {
				var1[var2 + 2] = (short) (var1[var2 + 2] | var3 ^ var1[var2] ^ var1[var2 + 1]);
			} else if (var1[var2 + 1] % 4 == 2) {
				var1[var2 + 2] = (short) (var1[var2 + 2] | var5 ^ var6 ^ var1[var2 + 1]);
			} else {
				var1[var2 + 2] = (short) (var1[var2 + 2] | var4 ^ var6 ^ var1[var2]);
			}
			if (var1[var2 + 2] % 7 == 0) {
				var1[var2 + 3] = (short) (var1[var2 + 3] | var4 ^ var5 ^ var6);
			} else if (var1[var2 + 2] % 7 == 1) {
				var1[var2 + 3] = (short) (var1[var2 + 3] | var6 ^ var1[var2] ^ var1[var2 + 1]);
			} else if (var1[var2 + 2] % 7 == 2) {
				var1[var2 + 3] = (short) (var1[var2 + 3] | var5 ^ var1[var2] ^ var1[var2 + 2]);
			} else if (var1[var2 + 2] % 7 == 3) {
				var1[var2 + 3] = (short) (var1[var2 + 3] | var4 ^ var1[var2 + 1] ^ var1[var2 + 2]);
			} else if (var1[var2 + 2] % 7 == 4) {
				var1[var2 + 3] = (short) (var1[var2 + 3] | var3 ^ var4 ^ var1[var2]);
			} else if (var1[var2 + 2] % 7 == 5) {
				var1[var2 + 3] = (short) (var1[var2 + 3] | var3 ^ var5 ^ var1[var2 + 1]);
			} else {
				var1[var2 + 3] = (short) (var1[var2 + 3] | var3 ^ var6 ^ var1[var2 + 2]);
			}
			var2 += 4;
		}
		int var16 = this.string.hashCode();
		byte var17 = 0;
		byte var18 = 0;
		var4 = (short) (var17 | var16);
		var5 = (short) (var18 | var16 >> 16);
		int var19 = -1;
		Hashtable<Short, Integer> var7 = new Hashtable<Short, Integer>();
		int var8;
		for (var10000 = var8 = 0; var10000 < var1.length; var10000 = var8) {
			var7.put(var1[var8], var8);
			if (var1[var8] == var4 && var1[var8 + 1] == var5) {
				if (var19 != -1) {
					throw new Exception();
				}

				var19 = var8;
			}

			var8 += 4;
		}
		if (var19 == -1) {
			throw new Exception();
		} else {
			short var9;
			for (short var20 = var9 = var1[var19 + 3]; var20 % 4 != 0; var20 = ++var9) {
			}
			byte[] var10;
			Arrays.fill(var10 = new byte[var9], (byte) 0);
			int var11 = 0;
			int var12 = var19 + 1;
			for (var10000 = var11; var10000 < var10.length; var10000 = var11) {
				var12 = ((Number) var7.get(new Short(var1[var12 + 1]))).shortValue();
				int var10001 = var11++;
				var10[var10001] = (byte) (var10[var10001] | var1[var12 + 2] >> 8);
				var10001 = var11++;
				var10[var10001] = (byte) (var10[var10001] | var1[var12 + 3] >> 8);
				var10001 = var11++;
				var10[var10001] = (byte) (var10[var10001] | var1[var12 + 2]);
				var10001 = var11++;
				var10[var10001] = (byte) (var10[var10001] | var1[var12 + 3]);
			}
			byte[] var13 = this.string.getBytes();
			int var14;
			for (var10000 = var14 = 0; var10000 < var10.length; var10000 = var14) {
				var10[var14] ^= var13[var14 % var13.length];
				++var14;
			}
			if (var10.length > 1) {
				var10[1] ^= var10[0];
			}
			int var15;
			for (var10000 = var15 = 2; var10000 < var10.length; var10000 = var15) {
				var10[var15] = (byte) (var10[var15] ^ var10[var15 - 1] ^ var10[var15 - 2]);
				++var15;
			}
			return new String(var10, 0, var1[var19 + 3], "UTF-8");
		}
	}

	// Method cleaned.
	public void createWatermark(String string) throws Exception_Sub1 {
		try {
			Vector<Class171> vector = this.method1761();
			if (this.method1763(vector)) {
				throw new Exception_Sub1("The jar file already contains watermark.");
			} else {
				Class34.arrangeOrder(vector);
				short[] arrShort = this.method1764(string);
				for (int i = vector.size() - arrShort.length / 4; i > 0; i--) {
					vector.remove(0);
				}
				this.method1760(vector, arrShort);
			}
		} catch (Exception e) {
			throw new Exception_Sub1("Watermarking error: " + e.getMessage());
		}
	}
}