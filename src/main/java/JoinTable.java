import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JoinTable {

    public static class TokenizerMapper2
            extends Mapper<Object, Text, Text, Text> {

        private Text val = new Text();
        private Text xKey = new Text();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String[] values =  value.toString().split(" ");
            val.set(values[2]);
            xKey.set(values[1]);
            context.write(xKey, val);
        }
    }

    public static class IntSumReducer2
            extends Reducer<Text, Text, Text, Text> {

        Text result = new Text();

        public void reduce(Text xkey, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {

            List<String> listY = new ArrayList<String>();
            List<String> listZ = new ArrayList<String>();
            for(Text val : values){
                if(val.toString().split("-")[0].equals("r")){
                    listY.add(val.toString());
                }else{
                    listZ.add(val.toString());
                }
            }
            for(String y : listY){
                for(String z : listZ){
                    result.set(y + " " + z);
                    context.write(xkey, result);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "jointable");
        job.setJarByClass(JoinTable.class);
        job.setMapperClass(TokenizerMapper2.class);
//        job.setCombinerClass(IntSumReducer2.class);
        job.setReducerClass(IntSumReducer2.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}