/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.apio.architect.test.util.internal.writer;

import static com.liferay.apio.architect.impl.internal.unsafe.Unsafe.unsafeCast;
import static com.liferay.apio.architect.test.util.representor.MockRepresentorCreator.createRootModelRepresentor;
import static com.liferay.apio.architect.test.util.writer.MockWriterUtil.getRequestInfo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.batch.BatchResult;
import com.liferay.apio.architect.impl.internal.message.json.BatchResultMessageMapper;
import com.liferay.apio.architect.impl.internal.request.RequestInfo;
import com.liferay.apio.architect.impl.internal.writer.BatchResultWriter;
import com.liferay.apio.architect.impl.internal.writer.BatchResultWriter.Builder;
import com.liferay.apio.architect.test.util.writer.MockWriterUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.ws.rs.core.HttpHeaders;

/**
 * Provides methods that test {@code BatchResultMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockBatchResultWriter {

	/**
	 * Writes a {@link BatchResult}.
	 *
	 * @param httpHeaders the request's {@code HttpHeaders}
	 * @param batchResultMessageMapper the {@link BatchResultMessageMapper} to
	 *        use for writing the JSON object
	 */
	public static JsonObject write(
		HttpHeaders httpHeaders,
		BatchResultMessageMapper<String> batchResultMessageMapper) {

		RequestInfo requestInfo = getRequestInfo(httpHeaders);

		List<String> identifiers = LongStream.rangeClosed(
			1, 7
		).mapToObj(
			String::valueOf
		).collect(
			Collectors.toList()
		);

		BatchResult<String> batchResult = new BatchResult<>(
			identifiers, "root");

		BatchResultWriter<String> batchResultWriter = Builder.batchResult(
			batchResult
		).batchResultMessageMapper(
			batchResultMessageMapper
		).pathFunction(
			MockWriterUtil::identifierToPath
		).representorFunction(
			__ -> Optional.of(unsafeCast(createRootModelRepresentor(false)))
		).requestInfo(
			requestInfo
		).build();

		Optional<String> optional = batchResultWriter.write();

		if (!optional.isPresent()) {
			throw new AssertionError("Writer failed to write");
		}

		return new Gson().fromJson(optional.get(), JsonObject.class);
	}

	private MockBatchResultWriter() {
		throw new UnsupportedOperationException();
	}

}