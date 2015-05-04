/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiWhiteSpace

public class JetFunctionLiteralArgument(node: ASTNode) : JetValueArgument(node) {

    private fun assertFL() = throw AssertionError("Function literal argument doesn't contain function literal expression: " +
                                                          "${super.getArgumentExpression()?.getText()} (it should be guaranteed by parser)")

    override fun getArgumentExpression() = super.getArgumentExpression() ?: assertFL()

    public fun getFunctionLiteral(): JetFunctionLiteralExpression = unpackFunctionLiteral(getArgumentExpression())

    private fun unpackFunctionLiteral(expression: JetExpression?): JetFunctionLiteralExpression =
            when (expression) {
                is JetFunctionLiteralExpression -> expression
                is JetLabeledExpression -> unpackFunctionLiteral(expression.getBaseExpression())
                is JetAnnotatedExpression -> unpackFunctionLiteral(expression.getBaseExpression())
                else -> assertFL()
            }
}

