%{
Information:
K-Nearest Neighbour Classification
Using k = 3
More info: https://uk.mathworks.com/help/stats/fitcknn.html#bt6cr9l-Mdl
%}

% Breast Cancer Dataset Classification
filename = 'breast-cancer-wisconsin.data';
delimiterIn = ',';
headerlinesIn = 0;

positiveclassrep = 4;
negativeclassrep = 2;

fid = fopen(filename, 'rt');
datacell = textscan(fid, '%f%f%f%f%f%f%f%f%f%f%f', 'Delimiter', delimiterIn, 'TreatAsEmpty', "?");
fclose(fid);

A = cell2mat(datacell);
A(:,1) = [];
A(any(isnan(A), 2), :) = [];

positiveCases = [];
negativeCases = [];

rng(10);
for i = 1:size(A,1)
    row = A(i,:);
    
    if row(end) == positiveclassrep
        positiveCases = [positiveCases;row];
    else
        negativeCases = [negativeCases;row];
    end
end

numoffolds = 5;
fold1 = [];
fold2 = [];
fold3 = [];
fold4 = [];
fold5 = [];

% Add positive cases to folds
for i = 1:size(positiveCases,1)
    foldnum = mod(i,numoffolds);
    row = positiveCases(i,:);
    
    if foldnum == 1
        fold1 = [fold1;row];
    elseif foldnum == 2
        fold2 = [fold2;row];
    elseif foldnum == 3
        fold3 = [fold3;row];
    elseif foldnum == 4
        fold4 = [fold4;row];
    else
        fold5 = [fold5;row];
    end
end

% Add negative cases to folds
for i = 1:size(negativeCases,1)
    foldnum = mod(i,numoffolds);
    row = negativeCases(i,:);
    
    if foldnum == 1
        fold1 = [fold1;row];
    elseif foldnum == 2
        fold2 = [fold2;row];
    elseif foldnum == 3
        fold3 = [fold3;row];
    elseif foldnum == 4
        fold4 = [fold4;row];
    else
        fold5 = [fold5;row];
    end
end

% Cross-Validation
totalaccuracy = 0;
totalprecision = 0;
totalrecall = 0;
totalfmeasure = 0;

for i = 1:numoffolds
    trainfolds = [];
    testfold = [];
    
    if foldnum == 1
        testfold = fold1;
        trainfolds = [trainfolds;fold2];
        trainfolds = [trainfolds;fold3];
        trainfolds = [trainfolds;fold4];
        trainfolds = [trainfolds;fold5];
    elseif foldnum == 2
        testfold = fold2;
        trainfolds = [trainfolds;fold1];
        trainfolds = [trainfolds;fold3];
        trainfolds = [trainfolds;fold4];
        trainfolds = [trainfolds;fold5];
    elseif foldnum == 3
        testfold = fold3;
        trainfolds = [trainfolds;fold1];
        trainfolds = [trainfolds;fold2];
        trainfolds = [trainfolds;fold4];
        trainfolds = [trainfolds;fold5];
    elseif foldnum == 4
        testfold = fold4;
        trainfolds = [trainfolds;fold1];
        trainfolds = [trainfolds;fold2];
        trainfolds = [trainfolds;fold3];
        trainfolds = [trainfolds;fold5];
    else
        testfold = fold5;
        trainfolds = [trainfolds;fold1];
        trainfolds = [trainfolds;fold2];
        trainfolds = [trainfolds;fold3];
        trainfolds = [trainfolds;fold4];
    end
    
    Ytrain = trainfolds(:,end);
    Xtrain = trainfolds(:,1:end-1);
    
    Ytest = testfold(:,end);
    Xtest = testfold(:,1:end-1);
    
    rng(10);
    Mdl = fitcknn(Xtrain,Ytrain,'NumNeighbors',1,'Standardize',1);
    predictedY = predict(Mdl, Xtest);
    cm = confusionchart(Ytest, predictedY);
    results = cm.NormalizedValues;
    
    tp = results(2,2);
    fp = results(1,2);
    fn = results(2,1);
    tn = results(1,1);
    
    accuracy = (tp+tn)/(tp+fp+fn+tn);
    precision = tp/(tp+fp);
    recall = tp/(tp+fn);
    
    b = 1;
    bsquared = b^2;
    fmeasure = (1+bsquared)*((precision*recall)/((bsquared*precision)+recall));
    
    totalaccuracy = totalaccuracy + accuracy;
    totalprecision = totalprecision + precision;
    totalrecall = totalrecall + recall;
    totalfmeasure = totalfmeasure + fmeasure;
end

averageaccuracy = totalaccuracy / numoffolds;
averageprecision = totalprecision / numoffolds;
averagerecall = totalrecall / numoffolds;
averagefmeasure = totalfmeasure / numoffolds;

disp("Accuracy: " + averageaccuracy);
disp("Precision: " + averageprecision);
disp("Recall: " + averagerecall);
disp("F-Measure: " + averagefmeasure);
