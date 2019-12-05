# OPAL
OPAL framework

Use PATO framework to generate program triples for a c program. Please use our forked version of PATO framework.

To create flow graph triples, run the following command in our forked version of PATO
swipl --nosignals --quiet -s run.pl -- file.ttl is.c.txt > file_cfg.ttl

To run propagate agent use Main.java in this project

To run Check agent use CheckAgent.java in this project
