package edu.clarkson.cs.clientlib.caida.itdk.model.routing

import edu.clarkson.cs.clientlib.caida.itdk.model.Routing
import com.google.common.hash.Funnels
import com.google.common.hash.BloomFilter
import edu.clarkson.cs.clientlib.lang.Properties
import scala.io.Source
import java.util.concurrent.ConcurrentHashMap

class DefaultRouting extends Routing {

  private val PROP = "routing.properties";

  private val bloomFilter = BloomFilter.create[Integer](
    Funnels.integerFunnel(),
    Properties.load[Int](PROP, "routing_size"), 0.001);

  private val hashIndex = new ConcurrentHashMap[Int, List[Int]];

  private def load = {
    var file = Properties.load[String](PROP, "routing_table");
    Source.fromFile(file).getLines().foreach(line => {
      var split = line.split("\\s");
      var nodeId = split(0).toInt;
      bloomFilter.put(nodeId);
      hashIndex.put(nodeId, split.slice(1, split.length).map(a => a.toInt).toList);
    });
  }

  def init = {
    load
  }

  def route(nodeId: Int): Iterable[Int] = {
    if (bloomFilter.mightContain(nodeId)) {
      if (hashIndex.containsKey(nodeId)) {
        return hashIndex.get(nodeId);
      }
    }
    return Iterable.empty[Int];
  }
}