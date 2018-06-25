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

package com.liferay.apio.architect.impl.internal.operation;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.operation.HTTPMethod;
import com.liferay.apio.architect.operation.Operation;

import java.util.Optional;

/**
 * Represents a resource update operation.
 *
 * @author Alejandro Hernández
 */
public class UpdateOperation implements Operation {

	public UpdateOperation(Form form, String resourceName) {
		_form = form;
		_resourceName = resourceName;
	}

	@Override
	public Optional<Form> getFormOptional() {
		return Optional.ofNullable(_form);
	}

	@Override
	public HTTPMethod getHttpMethod() {
		return HTTPMethod.PUT;
	}

	@Override
	public String getName() {
		return _resourceName + "/update";
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	private final Form _form;
	private final String _resourceName;

}