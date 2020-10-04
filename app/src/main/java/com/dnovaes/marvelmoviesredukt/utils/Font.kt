package com.dnovaes.marvelmoviesredukt.utils

import android.content.Context
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.getFont
import com.dnovaes.marvelmoviesredukt.R
import trikita.anvil.Anvil
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W100
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W200
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W300
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W400
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W500
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W600
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W700
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W800
import com.dnovaes.marvelmoviesredukt.utils.FontWeight.W900

enum class FontWeight {
    W100, W200, W300, W400, W500, W600, W700, W800, W900
}

object Font {

    fun fontWeight(context: Context, weight: FontWeight) {
        val view = Anvil.currentView() as? TextView ?: return
        view.typeface = getFont(context, when (weight) {
            W100 -> R.font.work_sans_thin
            W200 -> R.font.work_sans_extra_light
            W300 -> R.font.work_sans_light
            W400 -> R.font.work_sans_regular
            W500 -> R.font.work_sans_medium
            W600 -> R.font.work_sans_semi_bold
            W700 -> R.font.work_sans_bold
            W800 -> R.font.work_sans_extra_bold
            W900 -> R.font.work_sans_black
        })
    }
}