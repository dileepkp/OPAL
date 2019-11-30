/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

//import CFile;

/**
 *
 * @author aidb
 */
public class CEntity {
     String entityDeclaration;
    String entityExpression;
    String entityName;
    String cType;
    String baseType;
    String uri;
    String cCode;
    String structDeclRef;
    String rdfType;
    String scope;
    String structDeclName;

    CEntity(String e, String t) {
       // entity = e;
        cType = t;
    }

    CEntity() {

    }

    String getCCode() {
        
        if(entityName != null && !Utils.isArrayMemberAccess(rdfType)
                && (Utils.isScalarType(cType) || Utils.isArrayType(cType)
                    || Utils.isPointerToComplexType(cType, baseType)) ) 
            return entityName;
        
        
        
        if(cCode == null) {
        CStatementLocation csl = new CStatementLocation(entityExpression);
        if(csl.beginLine == 0)
            return null;
        if(csl.beginLine == csl.endLine)
            cCode = CFile.getCode(csl.beginLine, csl.beginCol, csl.endCol);
        else 
            cCode = CFile.getCode(csl.beginLine, csl.beginCol,csl.endLine, csl.endCol);
        
        if(Utils.isPointerToSimpleTypeExceptChar(cType, baseType))
            cCode = "*" + cCode;
        }
        return cCode;
    }
    
    String getCCodeOfStruct(){
        if(structDeclName != null)
            return structDeclName;
        return null;
      /*  String entityCode;
        CStatementLocation csl = new CStatementLocation(structDeclRef);
        if(csl.beginLine == 0)
            return null;
        if(csl.beginLine == csl.endLine)
            entityCode = CFile.getCode(csl.beginLine, csl.beginCol, csl.endCol);
        else 
            entityCode = CFile.getCode(csl.beginLine, csl.beginCol,csl.endLine, csl.endCol);
        
        return entityCode;*/
    }
    
    String getShortEntityDeclaration(){
        if(entityDeclaration == null)
            return getCCode();
        return entityDeclaration.replaceAll(Settings.fileIRI + "#", "file0:");
    }
    
    String getShortStructDeclRef(){
        return structDeclRef.replaceAll(Settings.fileIRI + "#", "file0:");
    }
}
