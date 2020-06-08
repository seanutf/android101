package com.seanutf.android.homewiki.list;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seanutf.android.base.homwiki.data.WikiData;
import com.seanutf.android.databinding.ActivityHomeWikiBinding;
import com.seanutf.android.homewiki.post.HomeWikiPostActivity;
import com.seanutf.cmmonui.arch.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.seanutf.android.base.router.RouterPathConstant.UI_APP_WIKI;

@Route(path = UI_APP_WIKI)
public class HomeWikiListActivity extends BaseActivity {

    ActivityHomeWikiBinding vb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityHomeWikiBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        setViews();
    }

    private void setViews() {
        vb.ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeWikiPostActivity.startActivity(HomeWikiListActivity.this);
            }
        });

        vb.rvWikiList.setLayoutManager(new LinearLayoutManager(this));
        HomWikiListAdapter adapter = new HomWikiListAdapter();
        vb.rvWikiList.setAdapter(adapter);
        adapter.setData(getData());

    }

    private void getWikiDetailData() {
        //        NetWorkManager.instance.getWikiList(wikiId, new CommonNetResponse(){
        //            @Override
        //            public void onRequestSuccess(WikiData data){
        //                doSuccess(data);
        //            }
        //
        //            @Override
        //            public void onRequestFail(int failCode, String failMsg){
        //                    doFail(failCode, failMsg);
        //            }
        //
        //            @Override
        //            public void onRequestError(int errorCode, String failMsg){
        //                doError(errorCode, failMsg);
        //            }
        //        });
    }


    private List<WikiData> getData() {
        List<WikiData> wikiDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            switch (i) {
                case 0:
                    WikiData data0 = new WikiData();
                    data0.id = i;
                    data0.title = "新手可以知道的20条厨房技巧";
                    data0.content = "我从小就在厨房给我妈打下手，执行的工作包括但不限于：剥蒜、洗菜、切葱、没洗干净再去多洗几次菜、从冰箱里拿个东西、在她忘记切某个原材料但是又已经炒上菜的时候紧急帮忙切菜、实在闲下来了就去洗一下刚刚装过东西的碗、在她看书的时候去厨房给她关正在煲汤的火...\n" +
                            "\n" +
                            "我小时候经常觉得……我看电视看得好好的，你好像其实也不是非我不可，干嘛非得拉一个不熟练的童工蹚厨房这趟浑水呢？大概就是看不得我在她忙的时候闲着，哦不，大概就是想培养一下我的厨房技能吧。\n" +
                            "\n" +
                            "事实证明颇有成效，待我大学毕业独立生活之后，发现无数我在厨房打下手过程中掌握的厨房小技巧都那么有用。那些我后来在烹饪书籍上看到的好多tips，都已经深深地印在我的脑海里。关于用刀、切菜、炒菜、食材保存、厨房清洁的这些技巧，我也想说给你听。";
                    data0.sortId = i;
                    data0.imgUrl = "https://tgi1.jia.com/zximg/201207/ecb3461f23c8.jpg";
                    wikiDataList.add(data0);
                    break;
                case 1:
                    WikiData data1 = new WikiData();
                    data1.id = i;
                    data1.title = "卫生间装修你需要知道的";
                    data1.content = "说到装修，装修房子最重要最难的两个区域:厨房和卫生间，其他区域讲究的更多是舒适和美观，而卫生间厨房除了上面两项之外更需要完善的功能性！尤其是卫生间，装修得不好很影响居住！而卫生间更多的是实用，而不是一味的看着美观，当然在实用的基础上我们会最大化的美观。\n" +
                            "\n";
                    data1.sortId = i;
                    data1.imgUrl = "https://tgi1.jia.com/zximg/201507/46fb5435d956.jpg";
                    wikiDataList.add(data1);
                    break;
                case 2:
                    WikiData data2 = new WikiData();
                    data2.id = i;
                    data2.title = "夏季居家消毒注意事情";
                    data2.content = "冲洗浸泡消毒法。要经常用流动水和肥皂洗手，特别是在饭前、便后、接触污染物品后。对于不适于高温煮沸的物品可用0.5％过氧乙酸浸泡0.5到1小时，或用5％漂白粉上清液（漂白粉沉淀后，上面的清水）浸泡30－60分钟，也可用含有效氯500毫克/升的洗清剂浸泡5－10分钟，取出后清水冲净。浸泡时消毒物品应完全被浸没。一些化纤织物、绸缎等只能采用化学浸泡消毒方法";
                    data2.sortId = i;
                    data2.imgUrl = "http://news.yntv.cn/content/18/201305/25/P_728278_0__526588513.jpg";
                    wikiDataList.add(data2);
                    break;
                case 3:
                    WikiData data3 = new WikiData();
                    data3.id = i;
                    data3.title = "针对新型冠状病毒的基本防护措施";
                    data3.content = "请随时关注世卫组织网站上以及国家和地方公共卫生机构提供的关于2019冠状病毒病疫情的最新信息。2019冠状病毒病主要仍影响中国，但其他国家也发生了一些疫情。大多数感染者症状轻微并会康复，但有些感染者可能会患重症。可以采取以下方式维护本人和他人健康：\n" +
                            "\n" +
                            "勤洗手\n" +
                            "\n" +
                            "如果双手不明显脏，经常用含酒精成分的免洗洗手液清洁手或用肥皂和清水洗手。  \n" +
                            "\n" +
                            "理由：用含酒精成分的免洗洗手液清洁手或用肥皂和清水洗手可以杀灭手上的病毒。\n" +
                            "\n" +
                            "保持社交距离";
                    data3.sortId = i;
                    data3.imgUrl = "http://inews.gtimg.com/newsapp_bt/0/11488691920/641";
                    wikiDataList.add(data3);
                    break;
                case 4:
                    WikiData data4 = new WikiData();
                    data4.id = i;
                    data4.title = "孩子经常揉眼睛";
                    data4.content = "有的宝宝在哭闹、玩耍甚至吃饭时，也会习惯性地揉眼睛，久而久之养成经常揉眼的不良习惯，频繁揉眼可能将手上的细菌带到眼部，并伤害宝宝的眼周皮肤。\n" +
                            "\n" +
                            "解决对策：当宝宝因哭闹揉眼时，应及时用柔软的纸巾帮他擦净眼泪，保持眼周清洁，这样可以减少宝宝揉眼睛的机会，避免养成揉眼的不良习惯。";
                    data4.sortId = i;
                    data4.imgUrl = "https://goss4.cfp.cn/creative/vcg/800/version23/VCG41108921174.jpg?x-oss-process=image/format,jpg/interlace,1";
                    wikiDataList.add(data4);
                    break;
                case 5:
                    WikiData data5 = new WikiData();
                    data5.id = i;
                    data5.title = "中老年人如何补钙";
                    data5.content = "由于骨质疏松是老年人常见的疾病。40岁以上的中老年人中，大约有15%患有骨质疏松症。年龄越大，发病率越高。但此病发生的迟早及速度，与从食物中摄入的钙量多少有直接关系。所以，中老年人一定要及时补钙，那么，老年人怎么补钙呢？";
                    data5.sortId = i;
                    data5.imgUrl = "http://www.guziyy.com/Attachments/201612/10/1481338771JdoDKGOooq.jpg.thumb.jpg";
                    wikiDataList.add(data5);
                    break;
                case 6:
                    WikiData data6 = new WikiData();
                    data6.id = i;
                    data6.title = "枸杞成为中年大叔养生标配";
                    data6.content = "枸杞是常见的药材和滋补品，中医很早就有“枸杞养生”的说法。《本草纲目》记载：“枸杞，补肾生精，养肝……明目安神，令人长寿。”枸杞全身都是宝，明李时珍《本草纲目》记载：“春采枸杞叶，名天精草；夏采花，名长生草；秋采子，名枸杞子；冬采根，名地骨皮”。不仅枸杞子可入药，枸杞的叶、花、根也是上等的美食补品。\n" +
                            "现代医学研究表明，枸杞含有胡萝卜素、甜菜碱、维生素A、维生素B1、维生素B2、维生素C和钙、磷、铁等多种营养成分，具有增加白细胞活性、促进肝细胞新生的药理作用，还可降血压、降血糖血脂。枸杞果皮中富含的有效成分之一枸杞多糖（LBP）对骨髓造血功能和各项细胞免疫指标有明显的增强作用。英国饮食专家也指出，枸杞中所含的胡萝卜素及多种维生素能有效预防心脏病和癌症。\n" +
                            "中医认为，枸杞味甘，性平，归肝经、肾经、肺经，具有补肝肾、益精血、明目的功效，对老年肝肾虚损者是一味有效补益抗衰老的药物。南朝医药家陶弘景认为枸杞有“补益精气，强盛阴道”的功效。《药性论》记载：“能补益精诸不足，易颜色，变白，明目，安神。”《食疗本草》云：坚筋耐老，除风，补益筋骨，能益人，去虚劳。《纲目》：滋肾，润肺，明目。";
                    data6.sortId = i;
                    data6.imgUrl = "https://tgi1.jia.com/zximg/201507/46fb5435d956.jpg";
                    wikiDataList.add(data6);
                    break;
                case 7:
                    WikiData data7 = new WikiData();
                    data7.id = i;
                    data7.title = "坚定文化自信 培育社会主义核心价值观";
                    data7.content = " 作为中华民族和华夏文化重要发祥地之一，革命圣地延安所在地，陕西历史文化资源丰富、红色文化资源璀璨，文化积淀十分深厚。黄帝陵、兵马俑、延安宝塔、秦岭、华山等，是中华文明、中国革命、中华地理的精神标识和自然标识。党的十八大以来，在习近平新时代中国特色社会主义思想指引下，陕西坚持发展社会主义先进文化，激发文化创新创造活力，强化文化担当，坚定文化自信，文化强省建设迈出铿锵步伐。学习宣传贯彻总书记的重要讲话重要指示精神，我们必须以高度的责任心和使命感，进一步加强文化建设，发掘和用好丰富的文化资源，坚定文化自信，推动文化大发展大繁荣，不断增强新时代追赶超越的精神动力。";
                    data7.sortId = i;
                    data7.imgUrl = "https://tgi1.jia.com/zximg/201507/46fb5435d956.jpg";
                    wikiDataList.add(data7);
                    break;
                default:
                    WikiData data = new WikiData();
                    data.id = i;
                    data.title = "马云的事迹";
                    data.content = "放眼看现在的互联网世界，要想在互联网有所发展，就越不过马云、马化腾、刘强东、李彦宏这些个高山。这些人的创业史可谓是十分传奇，就拿马云来说。他的电商和阿里巴巴造福了多少人，几乎全中国都在使用马云的支付宝。不过马云创业初期，为何会留下很多影像资料，难道他知道一定会成功？\n" +
                            "马云创业初期，为何会留下很多影像资料，难道他知道一定会成功？\n" +
                            "对马云有一定了解的人都知道，他原来只是一个英语老师，最开始做一些翻译工作。后来开始接触互联网，但是在那个时候，马云的很多想法都会与世人背离，不被人看好是很正常的事情。直到如今回头看，才知道是多么的有远见，但很多人都存在一个问题，那就是为什么在创业初期会留下很多影像资料？\n" +
                            "马云创业初期，为何会留下很多影像资料，难道他知道一定会成功？\n" +
                            "其实在马云的创业初期，有一个同事非常喜欢摄影，每次有重要的会议都会记录下来。包括马云也会拍视频记录下来，并不是像现在这样随便拍视频感觉好玩，而是留下一些影视资料当作记录，如果失败了也算是一种激励，要是成功还能拿来激励年轻人。在那个年代要想保存大量的影视资料也是比较困难的，就算没有创建阿里巴巴，同样会成功。\n" +
                            "马云创业初期，为何会留下很多影像资料，难道他知道一定会成功？\n" +
                            "马云曾经在国外的采访中表示，最后悔的事情就是创建阿里巴巴，很多人都觉得马云是在说笑，也有人觉得他是在吹牛。但是从马云的成就来说，即便没有阿里巴巴，同样能够发展得非常好。做中国黄页、中国招商、创建海博翻译社、当大学英语老师，不管是哪一个，在当时那个年代都非常有前途，马云是个真正的人才。\n" +
                            "马云创业初期，为何会留下很多影像资料，难道他知道一定会成功？\n" +
                            "现如今他退休了，功成身退的他也没有“养老”，而是继续从事公益，教育事业。他不知道自己一定会成功，但命运就是这样，冥冥之中自有安排，泥土从来不会掩盖黄金的光芒。对此，你有什么看法呢？";
                    data.sortId = i;
                    data.imgUrl = "https://tgi1.jia.com/zximg/201507/46fb5435d956.jpg";
                    wikiDataList.add(data);
                    break;
            }
        }
        return wikiDataList;
    }
}
