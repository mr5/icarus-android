package com.github.mr5.icarus.button;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;

/**
 * Button use in RichEditor for tags H1, H2, H3
 */

public class HeadingButton extends TextViewButton{

    public static final String NAME_HEADER_H1 = "h1_button";
    public static final String NAME_HEADER_H2 = "h2_button";
    public static final String NAME_HEADER_H3 = "h3_button";
    public static final String NAME_HEADER_H4 = "h4_button";
    public static final String NAME_HEADER_H5 = "h5_button";
    public static final String NAME_HEADER_H6 = "h6_button";

    /**
     * Constructor for initialization basic constructor
     *
     * @param aTextView view that was clicked
     * @param aIcarus   object, which manages content in webview
     */
    public HeadingButton(TextView aTextView, Icarus aIcarus){
        super(aTextView, aIcarus);
    }

    /**
     * set label for a button
     *
     * @param aName name button
     */
    public void setName(String aName) {
        this.name = aName; //for internal use of the base class
        setLabel(aName);
    }

    /**
     * sending a command to js to process the selected tag
     */
    public void command() {
        icarus.jsExec("editor.toolbar.buttons['" + name + "'].command('" + getTag(name) + "')");
    }

    /**
     * setting a label for a button
     *
     * @param aName name button
     */
    private void setLabel(String aName) {
        String htmlName = null;
        switch (aName) {
            case NAME_HEADER_H1:
                htmlName = "<b>H1</b>";
                break;
            case NAME_HEADER_H2:
                htmlName = "<b>H2</b>";
                break;
            case NAME_HEADER_H3:
                htmlName = "<b>H3</b>";
                break;
            case NAME_HEADER_H4:
                htmlName = "<b>H4</b>";
                break;
            case NAME_HEADER_H5:
                htmlName = "<b>H5</b>";
                break;
            case NAME_HEADER_H6:
                htmlName = "<b>H6</b>";
                break;
            default:
                htmlName = "<b>H</b>";
                break;
        }
        this.textView.setText(getStringFromHtml(htmlName));
    }

    /**
     * convert html to spanned
     *
     * @param aHtml html code to display a button label
     * @return spanned object
     */
    private Spanned getStringFromHtml(String aHtml) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return Html.fromHtml(aHtml, 0);
        } else {
            return Html.fromHtml(aHtml);
        }
    }

    /**
     * get tag from name button
     *
     * @param aName name button
     * @return name tag (example: h1)
     */
    private String getTag(String aName) {
        return aName.split("_button")[0];
    }


}
