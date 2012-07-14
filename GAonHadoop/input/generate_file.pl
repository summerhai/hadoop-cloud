  #!/usr/bin/perl -w
  use strict;
  
  my $range = 100;
  my $random_number = int(rand($range)) + 1;

  open (MYFILE, '>>data.csv');
  for (my $i=0;$i<10000;$i++){
	print MYFILE $i.",";
	print MYFILE $random_number ."\n";
	$random_number = int(rand($range)) + 1;
  }
  
  
