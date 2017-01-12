/*
 * Copyright 2000-2016 JetBrains s.r.o.
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
package org.jetbrains.plugins.groovy.codeInspection.changeToOperator.transformations;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.codeInspection.changeToOperator.ChangeToOperatorInspection.Options;
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementFactory;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrMethodCall;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrReferenceExpression;
import org.jetbrains.plugins.groovy.lang.psi.impl.utils.ParenthesesUtils;

public abstract class Transformation {

  public abstract boolean couldApply(@NotNull GrMethodCall methodCall, @NotNull Options options);

  public abstract void apply(@NotNull GrMethodCall methodCall, @NotNull Options options);

  @Nullable
  public static GrExpression getBase(@NotNull GrMethodCall callExpression) {
    GrExpression expression = callExpression.getInvokedExpression();
    GrReferenceExpression invokedExpression = (GrReferenceExpression)expression;
    return invokedExpression.getQualifierExpression();
  }

  @NotNull
  public static GrExpression parenthesize(@NotNull GrExpression expression, int parentPrecedence) {
    if (ParenthesesUtils.getPrecedence(expression) >= parentPrecedence) {
      return createParenthesizedExpr(expression);
    }
    return expression;
  }

  @NotNull
  private static GrExpression createParenthesizedExpr(@NotNull GrExpression expression) {
    GroovyPsiElementFactory factory = GroovyPsiElementFactory.getInstance(expression.getProject());
    return factory.createParenthesizedExpr(expression);
  }
}
