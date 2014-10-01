package edu.clarkson.cs.clientlib.caida.itdk.tool

/**
 * Link data is originally in the format of 
 * link <link_id> (<node_id>(:<ip>)?[ ])+
 * 
 * This tool convert it into the format of 
 * nodelinks <node_id>(:<ip>)? : (<link_id>[ ])+
 */
object ConvertLinkFileFormat extends App {

}