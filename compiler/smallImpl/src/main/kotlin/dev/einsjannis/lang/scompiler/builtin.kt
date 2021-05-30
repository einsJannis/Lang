package dev.einsjannis.lang.scompiler

import kotlin.String

object Type {

	object Integer : TypeDef {

		override val templateArgumentDefs: List<TemplateArgumentDef> = emptyList()

		override val name: kotlin.String = "Integer"

	}

	object Float : TypeDef {

		override val templateArgumentDefs: List<TemplateArgumentDef> = emptyList()

		override val name: kotlin.String = "Float"

	}

	object Character : TypeDef {

		override val templateArgumentDefs: List<TemplateArgumentDef> = emptyList()

		override val name: kotlin.String = "Character"

	}

	object String : TypeDef {

		override val templateArgumentDefs: List<TemplateArgumentDef> = emptyList()

		override val name: kotlin.String = "String"

	}

	object Boolean : TypeDef {

		override val templateArgumentDefs: List<TemplateArgumentDef> = emptyList()

		override val name: kotlin.String = "Boolean"

	}

}
