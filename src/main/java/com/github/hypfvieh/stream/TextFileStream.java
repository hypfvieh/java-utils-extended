package com.github.hypfvieh.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.stream.Stream;

import com.github.hypfvieh.common.SearchOrder;
import com.github.hypfvieh.util.FileIoUtil;

/**
 * Class to read a Textfile in a {@link Stream}&lt;String&gt; and do stream operations on it.
 *
 * @author hypfvieh
 * @since v1.0.1 - 2017-10-12
 */
public final class TextFileStream {

	private TextFileStream() {

	}

	public static Stream<String> readFileToStream(String _inputFile, Charset _charset) {
		return readFileToStream(FileIoUtil.openInputStreamForFile(_inputFile, SearchOrder.CUSTOM_PATH, SearchOrder.CLASS_PATH), _charset);
	}

	public static Stream<String> readFileToStream(File _inputFile, Charset _charset) {
		return readFileToStream(FileIoUtil.openInputStreamForFile(_inputFile.getAbsolutePath(), SearchOrder.CUSTOM_PATH, SearchOrder.CLASS_PATH), _charset);
	}

	/**
	 * Reads the file to stream.<br>
	 * This method is nearly equivalent to {@link java.nio.file.Files#lines(java.nio.file.Path, Charset) Files.lines(Path, Charset)}
	 * with the exception of accepting {@link InputStream} instead of {@link java.nio.file.Path Path}.
	 *
	 * @param _input InputStream to read
	 * @param _charset Charset to use
	 * @return Stream<String>
	 */
	public static Stream<String> readFileToStream(InputStream _input, Charset _charset) {
		CharsetDecoder decoder = _charset.newDecoder();
		Reader reader = new InputStreamReader(_input, decoder);

		BufferedReader br = new BufferedReader(reader);
		try {
			return br.lines().onClose(() -> {
				try {
					br.close();
				} catch (IOException _ex1) {
					throw new UncheckedIOException(_ex1);
				}
			});
		} catch (Error | RuntimeException e) {
			try {
				br.close();
			} catch (IOException _ex2) {
				try {
					e.addSuppressed(_ex2);
				} catch (Throwable ignore) {
				}
			}
			throw e;
		}
	}

}
