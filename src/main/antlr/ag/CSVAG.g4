grammar CSVAG;

@header {
package ag;
import java.util.*;
}

file
locals [int i = 0]
  : hdr
    { System.out.println("header: " + $hdr.text); }
    (rs += row[$hdr.text.split(",")] { $i++; System.out.println($row.vals); } )+
    { System.out.println("Totally " + $i + " rows");
      for (RowContext r : $rs) {
        System.out.println("Row token interval: " + r.getSourceInterval());
      }
    };
hdr : row[null] ;
row[String[] columns] returns [Map<String, String> vals = new HashMap<String, String>()]
  locals [int col = 0]
    : field { if ($columns != null) $vals.put(columns[$col++], $field.text); }
      (',' field { if ($columns != null) $vals.put(columns[$col++], $field.text); } )* ;

field : ID | NUMBER ;

ID : [a-z]+ ;
NUMBER : [0-9]+ ;
WS : [ \t\r\n]+ -> skip;