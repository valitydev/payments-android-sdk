/*
 *
 * Copyright 2019 RBKmoney
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package money.rbk.data.exception

import money.rbk.domain.entity.ApiError

internal sealed class ApiException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause)

internal class InternalServerError(code: Int) : ApiException(
    "Internal server error, code: $code"
)

internal class ClientError(code: Int, val error: ApiError) : ApiException(
    "Client error, code: $code"
)

internal class ResponseParsingException(stringBody: String, e: Exception) :
    ApiException(stringBody, e)
