// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.jetbrains.plugins.groovy.geb

import org.jetbrains.plugins.groovy.lang.psi.api.GrFunctionalExpression
import org.jetbrains.plugins.groovy.lang.psi.impl.statements.expressions.TypesUtil
import org.jetbrains.plugins.groovy.lang.psi.patterns.groovyClosure
import org.jetbrains.plugins.groovy.lang.psi.patterns.psiMethod
import org.jetbrains.plugins.groovy.lang.resolve.delegatesTo.DelegatesToInfo
import org.jetbrains.plugins.groovy.lang.resolve.delegatesTo.GrDelegatesToProvider

class GebBrowserDelegatesToProvider : GrDelegatesToProvider {

  private companion object {
    val pattern = groovyClosure().inMethod(psiMethod("geb.Browser", "drive"))
  }

  override fun getDelegatesToInfo(expression: GrFunctionalExpression): DelegatesToInfo? {
    return if (pattern.accepts(expression)) {
      DelegatesToInfo(TypesUtil.createType("geb.Browser", expression))
    }
    else {
      null
    }
  }
}