import optparse
#to run test: python statanal.py -f <filename>


def fileread(filename):
		
	AB = AC = AD = 0
	BA = BC = BD = 0
	CA = CB = CD = 0
	DA = DB = DC = 0
	counter = 0; totaltime = 0
	
	timeAB = timeAC = timeAD = 0
	timeBA = timeBC = timeBD = 0
	timeCA = timeCB = timeCD = 0
	timeDA = timeDB = timeDC = 0

	file = open(filename)
	
	for line in file.readlines()[2:]:
		counter += 1
		
		starttime = int(line.split(';')[0])
		endtime = int(line.split(';')[1])
		timetaken = endtime - starttime
		totaltime += timetaken
		startdest = line.split(';')[2]
		enddest = line.split(';')[3]
	
		if startdest == "Athens":
			if enddest == "Bonitsa":
				AB += 1
				timeAB += timetaken
			elif enddest == "Cesaloniki":
				AC += 1
				timeAC += timetaken
			elif enddest == "Delfoi":
				AD += 1
				timeAD += timetaken
		elif startdest == "Bonitsa":
			if enddest == "Athens":
				BA += 1
				timeBA += timetaken
			elif enddest == "Cesaloniki":
				BC += 1
				timeBC += timetaken
			elif enddest == "Delfoi":
				BD += 1
				timeBD += timetaken
		elif startdest == "Cesaloniki":
			if enddest == "Athens":
				CA +=1
				timeCA += timetaken
			elif enddest == "Bonitsa":
				CB +=1
				timeCB += timetaken
			elif enddest == "Delfoi":
				CD +=1
				timeCD += timetaken
		elif startdest == "Delfoi":
			if enddest == "Athens":
				DA +=1
				timeDA += timetaken
			elif enddest == "Bonitsa":
				DB +=1
				timeDB += timetaken
			elif enddest == "Cesaloniki":
				DC +=1
				timeDC += timetaken
	
	file.close()
	
	f = open(filename.strip('.txt') + '_analysis.txt','w')
	
	f.write('Total number of cars: ' + str(counter) + '\n')
	f.write('Average journey duration: ' + str(totaltime/counter) + '\n\n')
	
	f.write('From A: \n')
	f.write('	Total: ' + str(AB+AC+AD) + ', Average time: ' + str((timeAB+timeAC+timeAD)/(AB+AC+AD)) + '\n')
	f.write('	A->B:: Total: ' + str(AB) + ', Average time: ' + str(timeAB/AB) + '\n')
	f.write('	A->C:: Total: ' + str(AC) + ', Average time: ' + str(timeAC/AC) + '\n')
	f.write('	A->D:: Total: ' + str(AD) + ', Average time: ' + str(timeAD/AD) + '\n\n')
	
	f.write('From B: \n')
	f.write('	Total: ' + str(BA+BC+BD) + ', Average time: ' + str((timeBA+timeBC+timeBD)/(BA+BC+BD)) + '\n')
	f.write('	B->A:: Total: ' + str(BA) + ', Average time: ' + str(timeBA/BA) + '\n')
	f.write('	B->C:: Total: ' + str(BC) + ', Average time: ' + str(timeBC/BC) + '\n')
	f.write('	B->D:: Total: ' + str(BD) + ', Average time: ' + str(timeBD/BD) + '\n\n')
	
	f.write('From C: \n')
	f.write('	Total: ' + str(CA+CB+CD) + ', Average time: ' + str((timeCA+timeCB+timeCD)/(CA+CB+CD)) + '\n')
	f.write('	C->A:: Total: ' + str(CA) + ', Average time: ' + str(timeCA/CA) + '\n')
	f.write('	C->B:: Total: ' + str(CB) + ', Average time: ' + str(timeCB/CB) + '\n')
	f.write('	C->D:: Total: ' + str(CD) + ', Average time: ' + str(timeCD/CD) + '\n\n')
	
	f.write('From D: \n')
	f.write('	Total: ' + str(DA+DB+DC) + ', Average time: ' + str((timeDA+timeDB+timeDC)/(DA+DB+DC)) + '\n')
	f.write('	D->A:: Total: ' + str(DA) + ', Average time: ' + str(timeDA/DA) + '\n')
	f.write('	D->B:: Total: ' + str(DB) + ', Average time: ' + str(timeDB/DB) + '\n')
	f.write('	D->C:: Total: ' + str(DB) + ', Average time: ' + str(timeDC/DC) + '\n\n')
	
	f.write('To A: Total: ' + str(DA+BA+CA) + ', Average time: ' + str((timeDA+timeBA+timeCA)/(DA+BA+CA)) + '\n')
	f.write('To B: Total: ' + str(AB+CB+DB) + ', Average time: ' + str((timeAB+timeCB+timeDB)/(AB+CB+DB)) + '\n')
	f.write('To C: Total: ' + str(AC+BC+DC) + ', Average time: ' + str((timeAC+timeBC+timeDC)/(AC+BC+DC)) + '\n')
	f.write('To D: Total: ' + str(AD+BD+CD) + ', Average time: ' + str((timeAD+timeBD+timeCD)/(AD+BD+CD)) + '\n')
	
	f.close()
	
def main():
	parser = optparse.OptionParser("usage %prog "+\
		"-f <filename>")
	parser.add_option('-f', dest='dname', type='string',\
	help='specify file')
	(options, args) = parser.parse_args()
	if (options.dname == None):
		print parser.usage
		exit(0)
	else:
		dname = options.dname
	
	fileread(dname)
	
if __name__ == '__main__':
	main()
			