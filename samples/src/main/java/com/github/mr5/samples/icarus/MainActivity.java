package com.github.mr5.samples.icarus;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.mr5.icarus.Callback;
import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.TextViewToolbar;
import com.github.mr5.icarus.Toolbar;
import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.button.FontScaleButton;
import com.github.mr5.icarus.button.HeadingButton;
import com.github.mr5.icarus.button.TextViewButton;
import com.github.mr5.icarus.entity.Options;
import com.github.mr5.icarus.popover.FontScalePopoverImpl;
import com.github.mr5.icarus.popover.HtmlPopoverImpl;
import com.github.mr5.icarus.popover.ImagePopoverImpl;
import com.github.mr5.icarus.popover.LinkPopoverImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    WebView webView;

    protected Icarus icarus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.editor);
        TextViewToolbar toolbar = new TextViewToolbar();
        Options options = new Options();
        options.setPlaceholder("Input something...");
        //  img: ['src', 'alt', 'width', 'height', 'data-non-image']
        // a: ['href', 'target']
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height", "data-non-image"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));

        icarus = new Icarus(toolbar, options, webView);
        prepareToolbar(toolbar, icarus);
        icarus.loadCSS("file:///android_asset/editor.css");
        icarus.loadJs("file:///android_asset/test.js");
        icarus.render();
    }

    private Toolbar prepareToolbar(TextViewToolbar toolbar, Icarus icarus) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "Simditor.ttf");
        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put(Button.NAME_BOLD, R.id.button_bold);
        generalButtons.put(Button.NAME_OL, R.id.button_list_ol);
        generalButtons.put(Button.NAME_BLOCKQUOTE, R.id.button_blockquote);
        generalButtons.put(Button.NAME_HR, R.id.button_hr);
        generalButtons.put(Button.NAME_UL, R.id.button_list_ul);
        generalButtons.put(Button.NAME_ALIGN_LEFT, R.id.button_align_left);
        generalButtons.put(Button.NAME_ALIGN_CENTER, R.id.button_align_center);
        generalButtons.put(Button.NAME_ALIGN_RIGHT, R.id.button_align_right);
        generalButtons.put(Button.NAME_ITALIC, R.id.button_italic);
        generalButtons.put(Button.NAME_INDENT, R.id.button_indent);
        generalButtons.put(Button.NAME_OUTDENT, R.id.button_outdent);
        generalButtons.put(Button.NAME_CODE, R.id.button_math);
        generalButtons.put(Button.NAME_UNDERLINE, R.id.button_underline);
        generalButtons.put(Button.NAME_STRIKETHROUGH, R.id.button_strike_through);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }
        TextView linkButtonTextView = (TextView) findViewById(R.id.button_link);
        linkButtonTextView.setTypeface(iconfont);
        TextViewButton linkButton = new TextViewButton(linkButtonTextView, icarus);
        linkButton.setName(Button.NAME_LINK);
        linkButton.setPopover(new LinkPopoverImpl(linkButtonTextView, icarus));
        toolbar.addButton(linkButton);

        TextView imageButtonTextView = (TextView) findViewById(R.id.button_image);
        imageButtonTextView.setTypeface(iconfont);
        TextViewButton imageButton = new TextViewButton(imageButtonTextView, icarus);
        imageButton.setName(Button.NAME_IMAGE);
        imageButton.setPopover(new ImagePopoverImpl(imageButtonTextView, icarus));
        toolbar.addButton(imageButton);

        TextView htmlButtonTextView = (TextView) findViewById(R.id.button_html5);
        htmlButtonTextView.setTypeface(iconfont);
        TextViewButton htmlButton = new TextViewButton(htmlButtonTextView, icarus);
        htmlButton.setName(Button.NAME_HTML);
        htmlButton.setPopover(new HtmlPopoverImpl(htmlButtonTextView, icarus));
        toolbar.addButton(htmlButton);

        TextView fontScaleTextView = (TextView) findViewById(R.id.button_font_scale);
        fontScaleTextView.setTypeface(iconfont);
        TextViewButton fontScaleButton = new FontScaleButton(fontScaleTextView, icarus);
        fontScaleButton.setPopover(new FontScalePopoverImpl(fontScaleTextView, icarus));
        toolbar.addButton(fontScaleButton);

        TextView headingTextViewH1 = (TextView) findViewById(R.id.button_h1);
        headingTextViewH1.setTypeface(iconfont);
        TextViewButton headingH1Button = new HeadingButton(headingTextViewH1, icarus);
        headingH1Button.setName(HeadingButton.NAME_HEADER_H1);
        toolbar.addButton(headingH1Button);

        TextView headingTextViewH2 = (TextView) findViewById(R.id.button_h2);
        headingTextViewH1.setTypeface(iconfont);
        TextViewButton headingH2Button = new HeadingButton(headingTextViewH2, icarus);
        headingH2Button.setName(HeadingButton.NAME_HEADER_H2);
        toolbar.addButton(headingH2Button);

        TextView headingTextViewH3 = (TextView) findViewById(R.id.button_h3);
        headingTextViewH1.setTypeface(iconfont);
        TextViewButton headingH3Button = new HeadingButton(headingTextViewH3, icarus);
        headingH3Button.setName(HeadingButton.NAME_HEADER_H3);
        toolbar.addButton(headingH3Button);
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            icarus.render();
            return true;
        }
        if (id == R.id.action_get_content) {
            if (icarus == null) {
                return true;
            }
            icarus.getContent(new Callback() {
                @Override
                public void run(String params) {
                    Log.d("content", params);
                }
            });

            return true;
        }
        if (id == R.id.action_set_content) {
            if (icarus == null) {
                return true;
            }
            icarus.setContent(
                    "            <p><strong>本文作者为李迅雷，来源于微信公众号<a href=\"http://mp.weixin.qq.com/s?__biz=MzIyMTE0NjExMA==&amp;mid=2649619491&amp;idx=1&amp;sn=388f352821e853690066180966d039f2&amp;scene=23&amp;srcid=0417U7bOeAn8wQTbI9JjmnPo#rd\">lixunlei0722</a>。</strong></p>\n" +
                            "\n" +
                            "<p><strong>防风险成为今年头等大事</strong></p>\n" +
                            "\n" +
                            "<p>2016年前四个月过得太不容易了。新年伊始，人民币贬值预期大幅上升，这与央行在选定的“时间窗口”里推进汇改有关，从而导致国内外股市的大跌，虽然“熔断”一度被认为是罪魁祸首；尽管眼下人民币汇率基本被控制住了，但民间理财产品和信用债的违约事件却此起彼伏，又给金融市场带来不祥之兆；同时，猪肉、蔬菜价格和其他大宗商品价格狂涨，让通胀预期迅速攀升。</p>\n" +
                            "\n" +
                            "<p>去年五月《人民日报》权威人士的五问五答中曾有过这样一段话：“从一定意义上说，防风险就是稳增长。实现今年经济发展预期目标，须把握好稳增长和控风险的平衡，牢牢守住不发生系统性、区域性风险的底线。”</p>\n" +
                            "\n" +
                            "<p>我们不妨罗列一下今年金融市场的各大风险种类：汇率、信用债、民间理财产品、银行不良资产、股市中的高估值板块等。汇率通过管制手段基本可控；信用债目前大约有17只违约且没有完成兑付，其中绝大部分的债务人属于国企，这说明上层对打破刚兑还是痛下决心的，当然前提是风险可控；泛亚、E租宝、中晋公司等民间理财机构的违约事件估计不是个案，后续违约事件还会发酵，但考虑到民间理财机构的总规模有限，最大个案也只是百亿级的，故不至于导致系统性金融风险；银行信贷不良率虽存在一定低估，但即便实际情况很高，也不至于马上导致银行破产；股市同样存在估值偏高问题，但这是长期存在的问题了。</p>\n" +
                            "\n" +
                            "<p>从大金融下的多个市场看，均存在风险潜伏现象，且这些风险在今后较长时间内都挥之不去。假如这些看似可控的风险同时发酵，则爆发金融危机的概率就会大增。因此，金融管理当局在制定政策和对这些风险的处置上必然会小心翼翼，不敢大刀阔斧。</p>\n" +
                            "\n" +
                            "<hr>\n" +
                            "<p><strong>CPI又是一个新的风险变量</strong></p>\n" +
                            "\n" +
                            "<p>2016年1月份的CPI只有1.8%，到2月份一下子跳升至2.3%。如果说只是猪肉、蔬菜价格的上涨，这一季节性因素今后还是会消退的。但大宗商品价格的底部弹升，则会让核心CPI抬高，从而使得CPI的上涨更具有刚性。</p>\n" +
                            "\n" +
                            "<p>2016年政府工作报告把M2增速目标提高至13%，意味着M2-名义GDP（增速）的剪刀差超过6%，应该处于一个历史高位。M2的高增长给市场提供了充裕的流动性，这就是过去一年多来出现资产荒的逻辑。最近螺纹钢期货又出现大涨，吸引了大量资金进驻期货市场，反映了社会热钱的躁动，投机者利用民众对于通胀的预期或恐惧，发起一轮轮商品期货的炒作。</p>\n" +
                            "\n" +
                            "<p>从螺纹钢（HRB400,20MM）的现货价格走势看，最高的时候是在2008年，每吨价格超过5500元，2011年的某些月份也超过5000元，如今，即便已经从底部上涨了80%左右，还不到3000元，说明其价格水平仍是较低的。</p>\n" +
                            "\n" +
                            "<p><img alt=\"1\" src=\"http://posts.cdn.wallstcn.com/9f/33/34/1.png!article.foil\"></p>\n" +
                            "\n" +
                            "<p>海通期货研究员杨娜供图&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p>\n" +
                            "\n" +
                            "<p>因此，无论是贵金属的价格，还是大宗商品的价格，按照目前的价格水平，它们上涨的逻辑都是成立的。中国的PPI已经50个月处在负值区间了，故向上的动能也是积蓄已久，而PPI对CPI的传导也迟早会发生。</p>\n" +
                            "\n" +
                            "<hr>\n" +
                            "<p><strong>今年温和通胀的概率大</strong></p>\n" +
                            "\n" +
                            "<p>大家似乎存在这样一个逻辑：如果人民币对外不贬值，那么就会对内贬值，即出现通胀。而且，货币学派的理论也认为当M2增速明显超过名义GDP增速时，会发生通胀。中国当前似乎同时面临这两大现象。人民币汇率通过管制才得以稳定，而2015年M2增速减去名义GDP增速已经超过6%，今年估计也会维持在6%以上的高位。</p>\n" +
                            "\n" +
                            "<p>观察九十年代至今的四次通胀发生过程中，M2-名义GDP（增速）与CPI之间的关系，会发现每当M2-名义GDP（增速）为负时，CPI就达到了峰值。如1994年，CPI达到24.1%，M2增速为34.5%，名义GDP增速为36.4%；2011年CPI为5.4%，M2增速为13.6%，名义GDP增速为18.4%。</p>\n" +
                            "\n" +
                            "<p><img alt=\"2\" src=\"http://posts.cdn.wallstcn.com/fc/ea/42/2.png!article.foil\"></p>\n" +
                            "\n" +
                            "<p>进一步分析，发现CPI与名义GDP增速均为同步变化，即CPI的峰值无一例外地与名义GDP的峰值同步。也就是说，要使得通胀发生成为现实，名义GDP增速必须上行，而名义GDP中包含了物价上涨因素，其中PPI的上涨是关键的。</p>\n" +
                            "\n" +
                            "<p>就今年而言，PPI的负值虽然会收窄，但估计还很难变正，因此，名义GDP与实际GDP增速之差（缩减指数）估计也比较小，假如年末实际GDP增速为6.8%，名义GDP可能就在7%-7.2%之间，那么，M2-名义GDP（增速）估计还能维持5个百分点左右，即出现负值的可能性很小。不出现负值，就意味着CPI会继续上涨，但涨幅还没有达到峰值。当然，以上均为本人的主观判断，虽然这些主观判断是以中国经济的长期走势作为依据的。</p>\n" +
                            "\n" +
                            "<p>简言之，从以往的数据分析看，实际GDP增速可以作为通胀会否出现的主要依据。如果判断今年经济见底了，明年经济将上行，那么，通胀进一步上升的概率就加大了。不过，仅就今年而言，无论经济探底是否成功，CPI应该还会停留在2-3%之间，即维持温和通胀的概率较大。</p>\n" +
                            "\n" +
                            "<hr>\n" +
                            "<p><strong>明年会否出现高通胀</strong></p>\n" +
                            "\n" +
                            "<p>我预测2016年的CPI为2.8%，而海通宏观团队研究员于博在为我提供上一张图的同时，又做了下面一张图。他认为CPI与M2-名义GDP（增速）的负相关只是表象，实质上是CPI滞后M2-名义GDP（增速）两年的反应。若把M2-名义GDP（增速）曲线后移两年，则两条曲线的相关性就比较显著了，尤其是在2000年之后。</p>\n" +
                            "\n" +
                            "<p><img alt=\"3\" src=\"http://posts.cdn.wallstcn.com/07/9c/e7/3.png!article.foil\"></p>\n" +
                            "\n" +
                            "<p>2013-2014年，M2-名义GDP（增速）在3%-4%之间，变化不大，但2015年突然攀升至6.88%，主要是因为货币政策的放松（如分别连续五次降息和降准），这也是导致目前CPI上行的主要原因。</p>\n" +
                            "\n" +
                            "<p>按照以往经验，如果CPI上行，则货币政策开始收紧，M2增速便开始回落。如2009年实施两年四万亿投资计划，当年的M2增速达到27.7%，次年名义GDP增速达到18.3%，但央行同时进行加息和提高准备金率，连续两次加息，六次上调存款准备金率，最终导致M2增速回落至19.7%。2011年央行继续实施紧缩的货币政策，六次上调存准率，三次加息，至2011年M2增速则再度回落至13.6%。尽管2011年的CPI继续上升至5.4%，但已经是强弩之末。2011年下半年开始，大宗商品和贵金属的价格均出现暴跌，意味着次贷危机之后的刺激政策带动的这轮经济周期步入下行阶段。</p>\n" +
                            "\n" +
                            "<p>因此，M2-名义GDP（增速）为负，主要是靠货币政策收紧来实现的。同样，新一轮的通胀，又与货币政策的宽松有关，本轮宽松的货币政策起步于2014年11月份的降息，如果按CPI会滞后宽松货币政策两年来计算，则2017年的CPI会达到峰值，不过，货币政策的转向可能会发生在今年下半年。</p>\n" +
                            "\n" +
                            "<p>此外，我一直认为，CPI属于平民指数，在当今收入分配格局没有发生显著变化的情况下，贫富差距依然巨大，中低收入群体的现金资产规模有限，货币的流动性很大一部分体现在各类资产价格的波动上。部分资金会从金融资产领域流入到实物资产领域，对CPI的拉动作用不会特别大。</p>\n" +
                            "\n" +
                            "<p>由于本人不太认同将有新一轮经济周期开始之说，故估计GDP至多能在今年稳住，2017年略有反弹，但2018年之后经济将继续下台阶。因此，这轮通胀周期也将是小级别的，2017年的CPI估计至多到4%左右。</p>\n" +
                            "\n" +
                            "<hr>\n" +
                            "<p><strong>需要警惕货币政策转向的负面影响</strong></p>\n" +
                            "\n" +
                            "<p>历史会重复，但不会简单地重复。中国经济的体量越来越大，弹性也就越来越弱，波动幅度随之减小。以往经济周期的上行阶段，都是去库存和去杠杆的有利时机，但2009年之后，全社会的杠杆率水平就只上不下了，库存也去不尽了。一轮下行周期之后，除了大宗商品价格大幅回落了，金融资产和房地产的价格均处在历史的高位，劳动力价格继续上升，流动人口减少，人口老龄化。在这种条件下，货币政策的回旋余地也越来越小了。</p>\n" +
                            "\n" +
                            "<p>真要是GDP能起来，则货币政策收紧的问题也就不大了，就怕通胀上行而GDP不动。货币政策该如何应对呢？今年政府工作报告中，把CPI的目标定在3%，而央行货币政策的四大目标中，控制通胀是最重要的目标。央行应该不会等到CPI已经到3%才去收紧货币，我估计一旦CPI超过2.5%，央行就开始准备做政策调整了，超过2.8%则必定会有收紧举措。</p>\n" +
                            "\n" +
                            "<p>因此，通胀预期比人民币贬值预期更可怕，因为前者不可控，后者在资本管制条件下是可控的。2014年中央经济工作会议就提出了“高杠杆、泡沫化”问题，2016年这个问题愈加严重，如果通胀真的加重了，则货币政策将不得不收紧，这对于存在高泡沫问题的股市、债市、楼市都是利空。对于实体经济同样也是利空。</p>\n" +
                            "\n" +
                            "<p>不过，若按本人的判断，CPI的涨幅不会过大，3%多一点水平就可能止步，那么货币政策也不会大幅收紧，可能只是“温和转向”而已。货币政策转向总不是件好事，这一政策举措虽然有助于抑制风险爆发，但并没有解决经济的症结，充其量只是危机被延后而已。</p>\n" +
                            "\n" +
                            "<p>按照《人民日报》权威人士的说法，“供给侧结构性改革有一个窗口期，但窗口期不是无休止的，问题不会等我们，机遇更不会等我们。今天不以‘壮士断腕’的改革促发展，明天就可能面临更大的痛苦。”</p>\n" +
                            "\n" +
                            "<p>2016年中国经济在防范风险和稳增长的双重压力下，在贬值和通胀的双重预期下，能否还能大力度推进改革，的确是一个难题。</p>\n" +
                            "            <p>（更多精彩财经资讯，<a href=\"http://activity.wallstreetcn.com/app/index.html\">点击这里下载华尔街见闻App</a>)</p>\n"
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}