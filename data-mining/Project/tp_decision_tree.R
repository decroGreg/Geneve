#Eliott Loop
#Grégoire de Crombrugghe
#algorithme
#runAnalysis("D:/Geneve/data_mining/investing_program_prediction_data.csv",c(2,10:28),c(),28,rpart,c(5,10,50,100,200),c(0.001,0.01,0.1,0.2))



runAnalysis<- function(datasetName,DiscreteAttributesIndeces,AttrsToRemoveIndeces,ClassAttributeIndex,methode,minsplitVector,complexVector){
	indexes <- c(1:(ClassAttributeIndex))
 	#chargement des données
 
  	#Attribution des classes pour que naiveBayes calcule corrèctement les valeur données
 	colclasses <- ifelse(indexes %in% DiscreteAttributesIndeces, "factor", "numeric")
 	print(colclasses)
	myData <- read.table(file=datasetName, header=TRUE,sep=",",colClasses={colclasses})
	leaves<-matrix(nrow=length(minsplitVector),ncol=length(complexVector))
	nodes<-matrix(nrow=length(minsplitVector),ncol=length(complexVector))
	depths<-matrix(nrow=length(minsplitVector),ncol=length(complexVector))
	for(i in 1:length(minsplitVector)){
		for(j in 1:length(complexVector)){
			print(c("Minsplit : ",minsplitVector[i]," CP : ",complexVector[j]," Gini Index"))
			dimTree<-Experimentation(myData,DiscreteAttributesIndeces,AttrsToRemoveIndeces,ClassAttributeIndex,methode,minsplitVector[i],complexVector[j],FALSE)
			print(dimTree)
			
			print(c("Minsplit : ",minsplitVector[i]," CP : ",complexVector[j]," Information Gain"))
			dimTree<-Experimentation(myData,DiscreteAttributesIndeces,AttrsToRemoveIndeces,ClassAttributeIndex,methode,minsplitVector[i],complexVector[j],TRUE)
			print(dimTree)
			leaves[i,j]<-dimTree[1]
			depths[i,j]<-dimTree[2]
			nodes[i,j]<-dimTree[3]
		}
		
	}
	rownames(leaves)<-minsplitVector
	rownames(depths)<-minsplitVector
	rownames(nodes)<-minsplitVector
	barplot(t(leaves),beside=T,xlab="minsplit",main="number of leaves")
	windows()
	barplot(t(depths),beside=T,xlab="minsplit", main="the depth")
	windows()
	barplot(t(nodes),beside=T,xlab="minsplit",main="number of nodes")

	nb<-methode(InvType~.,data=myData,minsplit=100,cp=0.001)
	print("rpart with Gini index on the full dataset")
	print(nb)
	nbLeaves<-sum(nb$frame$var == "<leaf>")
	nodes <- as.numeric(rownames(nb$frame))
	depth<-max(rpart:::tree.depth(nodes))
	numNodes<-nrow(nb$frame)
	print(c(nbLeaves,depth,numNodes))
	windows()
	plot(nb,compress=T,uniform=T,margin=0.2)
	text(nb,digits=3,font=3,use.n=T,pretty=1)
	
	nb<-methode(InvType~.,data=myData,minsplit=100,cp=0.01,parms=list(split='information'))
	print("rpart with information gain on the full dataset")
	print(nb)
	nbLeaves<-sum(nb$frame$var == "<leaf>")
	nodes <- as.numeric(rownames(nb$frame))
	depth<-max(rpart:::tree.depth(nodes))
	numNodes<-nrow(nb$frame)
	print(c(nbLeaves,depth,numNodes))

	windows()
	plot(nb,compress=T,uniform=T,margin=0.2)
	text(nb,digits=3,font=3,use.n=T,pretty=1)
}

Experimentation<-function(myData,DiscreteAttributesIndeces,AttrsToRemoveIndeces,ClassAttributeIndex,methode,minpli,complex,InfoGainFlag){
	set.seed(1:5)
	average<-c()
	nb<-c()
	for(i in 1:5){
		#create model
		set.seed(i)
		trainIndex<-sample(1:dim(myData)[1],size=2/3*dim(myData)[1])
		trainData<-myData[trainIndex,]		
		if(InfoGainFlag){
		nb<-methode(InvType~.,data=trainData,minsplit=minpli,cp=complex,parms=list(split='information'))
		}else{
		nb<-methode(InvType~.,data=trainData,minsplit=minpli,cp=complex)
		}
		
		#print(nb)

		#prediction
		testData<-myData[-trainIndex,]
		pred<-predict(nb,testData[1:27],type="class")
		#print("prediction")
		#print(pred)

		#Accuracy computation
		CorrectWrong<-(pred==testData[,ClassAttributeIndex])
		numCorrect<-length(which(CorrectWrong))
		
		accuracy<-numCorrect/dim(testData)[1]
		average<-append(average,accuracy)
		#print("accuracy")
		#print(accuracy)
		
	}
	print("Compute average Accuracy")
	average<-mean(average)
	print(average)
	#[1]=nbLeaves,[2]=depth,[3]=numNodes
	nbLeaves<-sum(nb$frame$var == "<leaf>")
	nodes <- as.numeric(rownames(nb$frame))
	depth<-max(rpart:::tree.depth(nodes))
	numNodes<-nrow(nb$frame)
	return (c(nbLeaves,depth,numNodes))

}
