package com.richard.novel.widget.commentText;

/**
 * By Richard on 2018/1/4.
 */

import android.text.style.ClickableSpan;
import android.view.View;

public abstract class LongClickableSpan extends ClickableSpan {

    abstract public void onLongClick(View view);

}
