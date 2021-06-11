#Eliott Loop
#Grégoire Decrombrugghe
#runAnalysis("investing_program_prediction_data.csv",c(2,10:27),c(),28)
runAnalysis<- function(datasetName,DiscreteAttributesIndeces,AttrsToRemoveIndeces,ClassAttributeIndex){
	myData <- read.table(file=datasetName, header=TRUE,sep=",")
	for(i in 1:(dim(myData)[2])){
		print(colnames(myData)[i])
		if(i %in% DiscreteAttributesIndeces){
			#Entropie nomralisé
			print (paste("Entropy =",Entropy(myData[,i])/log2(length(unique(myData[,i])))))
			
			#information gain
			print (paste("information gain=",InformationGain(myData[,i],myData[,ClassAttributeIndex])))	
		}else{
			if(i==ClassAttributeIndex){
				print (paste("Entropy =",Entropy(myData[,i])/log2(length(unique(myData[,i])))))
			}else{
			dataSet<-unique(myData[,i])
			dataSet<-sort(dataSet)
			max<-0
			for(value in dataSet){
				infoGain<-InformationGain(as.factor(myData[,i]<value),myData[,ClassAttributeIndex])
				if(max<infoGain){
					max=infoGain
				}
			}
		}
			print (paste("information gain =",max))
		}
	}
}
Entropy<-function(x){
	p<-prop.table(table(x))
	-sum(p*log2(p))
}
#y=variable cible
ConditionalEntropy <-function (x,y){
	CondProp<- prop.table(table(x,y),1)
	res<-CondProp *log2(CondProp)
	res[is.nan(res)]<-0
	sum(-rowSums(res)*prop.table(table(x)))
}
InformationGain <-function(x,y){
	Entropy(y) - ConditionalEntropy(x,y)
}
