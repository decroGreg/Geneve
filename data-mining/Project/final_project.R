#Eliott Loop
#Grégoire de Crombrugghe
#algorithme
#runAnalysis("D:/Geneve/data_mining/investing_program_prediction_data.csv",c(2,10:28),c(1,3:9),c(),28)

library("e1071",lib.loc="G:\\R")
library(rpart)
library(class)

source("utilities.R")

runAnalysis<- function(datasetName,DiscreteAttributesIndeces,ContinuousAttributesIndeces,AttrsToRemoveIndeces,ClassAttributeIndex){
#recuperer mydata
indexes <- c(1:(ClassAttributeIndex))
 	#chargement des données
 
  	#Attribution des classes pour que naiveBayes calcule corrèctement les valeur données
 	colclasses <- ifelse(indexes %in% DiscreteAttributesIndeces, "factor", "numeric")
	myData <- read.table(file=datasetName, header=TRUE,sep=",",colClasses={colclasses})
	
	#4734 instances
#partitionner mydata 
	V<-9#nombre de partitions égales
	
	#Declaration des variables
	blockSize<-nrow(myData)%/%V
	partitionPrec<-1
	averageAccuracyNB<-c()
	correctWrongNB<-c()
	averageAccuracyRpart<-c()
	correctWrongRpart<-c()
	averageAccuracyKNN<-c()
	correctWrongKNN<-c()
	averageErrorDefaultClassifier<-c()
	
	#parcourir les partitions et appliquer les modeles de classification
	for(j in 1:V){
	#definir les partitions train & test
	limitPartition<-((partitionPrec+blockSize)-1)
	tst<-myData[partitionPrec:limitPartition,]
	trn<-myData[-(partitionPrec:limitPartition),]
	
	#NaiveBayes
	CorrectWrong<-NaiveBayes(tst,trn,ClassAttributeIndex)
	numCorrect<-length(which(CorrectWrong))
	accuracy<-numCorrect/dim(tst)[1]
	averageAccuracyNB<-append(averageAccuracyNB,(1-accuracy))	
	correctWrongNB<-append(correctWrongNB,CorrectWrong)
	
	#Rpart
	CorrectWrong<-Rpart(tst,trn,ClassAttributeIndex)
	numCorrect<-length(which(CorrectWrong))
	accuracy<-numCorrect/dim(tst)[1]
	averageAccuracyRpart<-append(averageAccuracyRpart,(1-accuracy))	
	correctWrongRpart<-append(correctWrongRpart,CorrectWrong)
	
	#KNN
	correctWrong<-KNN(tst,trn,ClassAttributeIndex,ContinuousAttributesIndeces,DiscreteAttributesIndeces)
	averageAccuracyKNN<-append(averageAccuracyKNN,correctWrong$error)
	correctWrongKNN<-append(correctWrongKNN,correctWrong$knn)
	print(correctWrong$knn)
	#DefaultClassifier
	averageErrorDefaultClassifier<-append(averageErrorDefaultClassifier,DefaultClassifier(tst,trn,ClassAttributeIndex))
	
	partitionPrec<-(limitPartition)

	}
	print(length(which(correctWrongRpart==FALSE)))
	print("Compute average Errors NB")
	averageAccuracyNB<-mean(averageAccuracyNB)
	print(averageAccuracyNB)
	
	print("Compute average Errors Decision tree")
	averageAccuracyRpart<-mean(averageAccuracyRpart)
	print(averageAccuracyRpart)
	
	print("Compute average Errors Nearest Neighbors")
	averageAccuracyKNN<-mean(averageAccuracyKNN)
	print(averageAccuracyKNN)
	
	print("Compute Average Error Accuracy ")
	averageErrorDefaultClassifier<-mean(averageErrorDefaultClassifier)
	print(averageErrorDefaultClassifier)
	
	#McNemar
	print("----McNemar----")
	print("McNemar test between NaiveBayes and Rpart")
	MCNEMAR(correctWrongNB,correctWrongRpart)

	print("McNemar test between NaiveBayes and KNN")
	correctWrongKNN<-(correctWrongKNN==2)
	MCNEMAR(correctWrongNB,correctWrongKNN)
	
	print("McNemar test between Rpart and KNN")
	MCNEMAR(correctWrongRpart,correctWrongKNN)
	
	MCNEMAR(correctWrongKNN,correctWrongKNN)


}
MCNEMAR<-function(correctWrongA,correctWrongB){
	print(table(correctWrongA))
	print(table(correctWrongB))
	falseA_B<-length(which(correctWrongA==FALSE &correctWrongB==FALSE))
	trueA_B<-length(which(correctWrongA==TRUE&correctWrongB==TRUE))
	Afalse_Btrue<-length(which(correctWrongA==FALSE&correctWrongB))
	Atrue_Bfalse<-length(which(correctWrongA&correctWrongB==FALSE))
	print(Afalse_Btrue)
	print(Atrue_Bfalse)
	m<-matrix(c(falseA_B,Afalse_Btrue,Atrue_Bfalse,trueA_B),nrow=2,dimnames=list("Classifier-B" = c("#wrong", "#correct"),"Classifier-A" = c("#wrong", "#correct")))
	print(mcnemar.test(m))

}





NaiveBayes<-function(tst,trn,ClassAttributeIndex){
	nb<-naiveBayes(InvType~.,data=trn)
	#print(nb)
	pred<-predict(nb,tst[1:27])
	#print(pred)
	
	#Accuracy computation
		CorrectWrong<-(pred==tst[,ClassAttributeIndex])
		return (CorrectWrong)
}

Rpart<-function(tst,trn,ClassAttributeIndex){
tree<-rpart(InvType~.,data=trn,minsplit=100,cp=0.001,parms=list(split='information'))
#prediction
		
		pred<-predict(tree,tst[1:27],type="class")
		#print("prediction")
		#print(pred)

		#Accuracy computation
		CorrectWrong<-(pred==tst[,ClassAttributeIndex])
		return (CorrectWrong)
}

KNN<-function(tst,trn,ClassAttributeIndex,ContinuousAttributesIndeces,DiscreteAttributesIndeces){
#tranformer les attribut continus et discrets
continuousAttr<-normalize(tst[,ContinuousAttributesIndeces])
discreteAttr<-discreteToNumeric(tst[,DiscreteAttributesIndeces])
tst<-cbind(continuousAttr,discreteAttr)

continuousAttr<-normalize(trn[,ContinuousAttributesIndeces])
discreteAttr<-discreteToNumeric(trn[,DiscreteAttributesIndeces])
trn<-cbind(continuousAttr,discreteAttr)

knn<-knn(trn[,1:(dim(trn)[2]-1)],tst[,-dim(tst)[2]],trn[,dim(trn)[2]],50)

r<-list("knn"=knn,"error"=(1-mean(knn==tst[,dim(tst)[2]])))

return (r)
}

DefaultClassifier<-function(tst,trn,ClassAttributeIndex){
	default_classifier<-prop.table(table(trn[,ClassAttributeIndex]))
	classMajority<-default_classifier[default_classifier==max(default_classifier)]
	propTestData<-prop.table(table(tst[,dim(trn)[2]]))
	accuracy<-propTestData[names(propTestData)==names(classMajority)]
	return (1-accuracy)
}

