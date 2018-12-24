# LanguageDecisionTree

A java program written for Intro to Intelligent Systems. Takes a set of labeled examples,
text in either English or Dutch with an E or a D at the end to signify what language, and
builds a decision tree from those examples to guess the language of another set of 
unlabeled examples. It also uses Adaptive Boosting to create an array of decision stumps
to provide another method for guessing the language of the unlabeled examples. More 
information on these two learning algorithms and the advantages to using either can be
found below.

## Decision Trees

[Decision Trees on wikipedia](https://en.wikipedia.org/wiki/Decision_tree_learning)

Decision Trees are used often in learning algorithms. Advantages over Adaptive Boosting
include better ability to guess an example properly and greater resistance to outliers.
Their weakness is a tendency to _overfit_ to a problem and create a much more compicated
tree than is neccesary to solve the problem reliably. 

## Adaptive Boosting

[Adaptive Boosting on wikipedia](https://en.wikipedia.org/wiki/AdaBoost)

Adaptive Boosting algorithms use other learners, such as Decision Trees, to improve
performance. Their advantage over Decision Trees is mainly greater performance. An
Adaptive Boosting algorithm will take as many steps as is designated by the program
and can even be easily made to limit those steps so that each one is at least a certain
amount of increase in accuracy.

## In Context

For the data used in this project there are not enough attributes or a high enough 
likelihood of unique outliers to warrant sacrificing the higher accuracy of Decision 
Tree learning for the improved performance of Adaptive Boosting.

## Usage

Run jar file in the dist directory from the command line using:

	java -jar LDT.jar Examples.txt Predict.txt

where the text files are the examples the program will build a decision tree from
and the examples the program will attempt to predict. Feel free to edit both files
to try out different data sets, they will work as long as the last character on each
line of Examples.txt is either an E or a D depending on the language of the line.

**Please Note:** You must use the jar file to run the program rather than simply compiling and running it as javac will produce an error with character encodings for some of the accented characters.
