#Eliott Loop
#Grégoire de Crombrugghe

library("e1071",lib.loc="g:\\R")


#algorithme
#runAnalysis("D:/Genève/data_mining/investing_program_prediction_data.csv",c(2,10:28),c(),28,naiveBayes)

runAnalysis<- function(datasetName,DiscreteAttributesIndeces,AttrsToRemoveIndeces,ClassAttributeIndex,methode){
	indexes <- c(1:(ClassAttributeIndex))
 	#chargement des données
 
  	#Attribution des classes pour que naiveBayes calcule corrèctement les valeur données
 	colclasses <- ifelse(indexes %in% DiscreteAttributesIndeces, "factor", "numeric")
 	print(colclasses)
	myData <- read.table(file=datasetName, header=TRUE,sep=",",colClasses={colclasses})
	set.seed(1:5)
	average<-c()
	for(i in 1:5){
		#create model
		#le faire 5 fois tp3 + calculer la moyenne des nombres de fois qui est correctec
		set.seed(i)
		trainIndex<-sample(1:dim(myData)[1],size=2/3*dim(myData)[1])
		trainData<-myData[trainIndex,]
		nb<-methode(InvType~.,data=trainData)
		print(nb)
		#prediction
		testData<-myData[-trainIndex,]
		pred<-predict(nb,testData[1:27])
		print(pred)

		#Accuracy computation
		CorrectWrong<-(pred==testData[,ClassAttributeIndex])
		numCorrect<-length(which(CorrectWrong))
		accuracy<-numCorrect/dim(testData)[1]
		average<-append(average,accuracy)
		print("accuracy")
		print(accuracy)

		#default classifier
		default_classifier<-prop.table(table(trainData[,ClassAttributeIndex]))
		print("default classifier")
		print(default_classifier)
		print("Naive bayes compare  to max Default classifier")
		print(accuracy-max(default_classifier))

	}
	print("Compute average Accuracy")
	average<-mean(average)
	print(average)

	

	nb<-naiveBayes(InvType~.,data=myData)
	print(nb)
	#barplot de 3attributs discret en utilisant le modèle changer pour que ça puisse adapter à n'importe quelle jeux de données
	windows()
	barplot(t(nb$tables$PE15))


}
