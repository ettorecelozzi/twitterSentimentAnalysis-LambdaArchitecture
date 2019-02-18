package fastlayer.storm;

import com.backtype.support.Utils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TweetSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private List<String> records;
    private int dbcounter;

    //open is called during initialization by storm and the SpoutOutputCollector is where the output will be sent
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        this.dbcounter = 0;
        String filename = "db.txt";
        this.records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            reader.close();
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }
    }

    //colled again during initialization to know how the tuples generated are structured
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }

    public void nextTuple() {
        Utils.sleep(100);
        String line = records.get(dbcounter);
        dbcounter++;
        collector.emit(new Values(line));
    }
}