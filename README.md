## icarus-android
Maybe the best rich text editor on android platform. Base on [Simditor](https://github.com/mycolorway/simditor)

## Features
* Alignment (left/center/right)
* Bold
* Blockquote
* Code
* Horizontal rule
* Italic
* Image
* Indent
* Link
* Outdent
* Ordered List
* Unordered List
* Underline
* Raw html (Insert anything to any selection range that you want via API)

## Usage
Add this line to your `build.gradle` file under your module directory.
```groovy
compile 'com.github.mr5:icarus:0.1.1-SNAPSHOT'
```
Java codes:
```java
import android.app.Activity;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.mr5.icarus.entity.Options;
import com.github.mr5.icarus.button.TextViewButton;
class EditorActivity extends Activity {
	protected WebView webView;
    protected Icarus icarus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get WebView from your layout, or create it manually.
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.editor);
        // I offered a toolbar to manage editor buttons which implements TextView that with icon fonts. 
        // It's just a collection, not a Android View implementation. 
        // TextViewToolbar will listen click events on all buttons that added to it. 
        // You can implement your own `Toolbar`, to prevent these default behaviors.
        TextViewToolbar toolbar = new TextViewToolbar();
        Options options = new Options();
        options.setPlaceholder("Placeholder...");
        icarus = new Icarus(toolbar, options, webView);
        TextView boldButton = new TextViewButton()
        boldButton.setName("bold");
		toolbar.addButton(boldButton);
        icarus.render();
    }
 }
```

[Sample](https://github.com/mr5/icarus-android/tree/master/samples)
